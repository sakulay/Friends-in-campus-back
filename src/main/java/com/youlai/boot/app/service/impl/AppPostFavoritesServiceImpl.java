package com.youlai.boot.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.boot.app.model.entity.AppPostLike;
import com.youlai.boot.app.model.mapper.AppPostFavoritesMapper;
import com.youlai.boot.app.model.query.AppPostQuery;
import com.youlai.boot.app.model.vo.AppPostBrowseRecordVO;
import com.youlai.boot.app.model.vo.AppPostVO;
import com.youlai.boot.app.service.AppPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.service.AppPostFavoritesService;
import com.youlai.boot.app.model.entity.AppPostFavorites;
import com.youlai.boot.app.model.form.AppPostFavoritesForm;
import com.youlai.boot.app.model.query.AppPostFavoritesQuery;
import com.youlai.boot.app.model.vo.AppPostFavoritesVO;
import com.youlai.boot.app.converter.AppPostFavoritesConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * 帖子收藏记录服务实现类
 *
 * @author yuyu
 * @since 2025-05-04 12:22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AppPostFavoritesServiceImpl extends ServiceImpl<AppPostFavoritesMapper, AppPostFavorites> implements AppPostFavoritesService {

    private final AppPostFavoritesConverter appPostFavoritesConverter;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AppPostService appPostService;
    private static final String POST_PAGE_KEY = "app:post:page:";
    private static final String POST_DETAIL_KEY = "app:post:detail:";
    private static final String POST_LIKE_COUNT_KEY = "app:post:like:count:";
    private static final String POST_FAVORITE_COUNT_KEY = "app:post:favorite:count:";
    /**
    * 获取帖子收藏记录分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppPostFavoritesVO>} 帖子收藏记录分页列表
    */
    @Override
    public IPage<AppPostVO> getAppPostFavoritesPage(AppPostFavoritesQuery queryParams) {
        Page<AppPostFavoritesVO> favoritesPage = this.baseMapper.getAppPostFavoritesPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 如果没有浏览记录，直接返回空分页
        if (favoritesPage.getRecords().isEmpty()) {
            return new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        }

        // 获取所有浏览的帖子ID
        List<Long> postIds = favoritesPage.getRecords().stream()
                .map(AppPostFavoritesVO::getPostId)
                .collect(Collectors.toList());

        // 构建帖子查询参数
        AppPostQuery postQuery = new AppPostQuery();
        postQuery.setPageNum(1);
        postQuery.setPageSize(postIds.size());
        postQuery.setStatus(1); // 只查询已审核通过的帖子
        postQuery.setPostIds(postIds); // 设置要查询的帖子ID列表
        postQuery.setOrderByPostIds(true); // 按照postIds顺序排序

        // 获取帖子信息
        IPage<AppPostVO> postPage = appPostService.getAppPostPage(postQuery);

        // 返回帖子分页数据
        return postPage;
    }
    
    /**
     * 获取帖子收藏记录表单数据
     *
     * @param id 帖子收藏记录ID
     * @return
     */
    @Override
    public AppPostFavoritesForm getAppPostFavoritesFormData(Long id) {
        AppPostFavorites entity = this.getById(id);
        return appPostFavoritesConverter.toForm(entity);
    }
    
    /**
     * 新增帖子收藏记录
     *
     * @param formData 帖子收藏记录表单对象
     * @return
     */
    @Override
    public boolean saveAppPostFavorites(AppPostFavoritesForm formData) {
        // 检查是否已经收藏
        AppPostFavorites existingFavorite = this.getOne(
                new LambdaQueryWrapper<AppPostFavorites>()
                        .eq(AppPostFavorites::getUserId, formData.getUserId())
                        .eq(AppPostFavorites::getPostId, formData.getPostId())
                        .eq(AppPostFavorites::getIsDeleted, 0)
        );

        boolean result;
        if (existingFavorite != null) {
            return false;
        } else {
            // 如果未点赞，则新增点赞记录
            AppPostFavorites entity = appPostFavoritesConverter.toEntity(formData);
            entity.setIsDeleted(0);
            result = this.save(entity);
        }
        // 清除相关缓存
        if (result) {
            clearPostCache(formData.getPostId());
        }

        return result;
    }
    
    /**
     * 更新帖子收藏记录
     *
     * @param id   帖子收藏记录ID
     * @param formData 帖子收藏记录表单对象
     * @return
     */
    @Override
    public boolean updateAppPostFavorites(Long id,AppPostFavoritesForm formData) {
        AppPostFavorites entity = appPostFavoritesConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除帖子收藏记录
     *
     * @param ids 帖子收藏记录ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppPostFavoritess(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的帖子收藏记录数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    @Override
    @Transactional
    public boolean cancelFavorite(Long userId, Long postId) {
        log.info("开始取消收藏，userId: {}, postId: {}", userId, postId);
        // 删除收藏记录
        AppPostFavorites existingFavorite = this.getOne(new LambdaQueryWrapper<AppPostFavorites>()
                .eq(AppPostFavorites::getUserId, userId)
                .eq(AppPostFavorites::getPostId, postId)
                .eq(AppPostFavorites::getIsDeleted, 0)
        );

        if (existingFavorite != null) {
            log.info("找到收藏记录，id: {}", existingFavorite.getId());

            // 使用 LambdaUpdateWrapper 进行更新
            boolean result = this.update(
                    new LambdaUpdateWrapper<AppPostFavorites>()
                            .eq(AppPostFavorites::getId, existingFavorite.getId())
                            .eq(AppPostFavorites::getIsDeleted, 0)
                            .set(AppPostFavorites::getIsDeleted, 1)
            );

            log.info("更新收藏记录结果: {}", result);

            if (result) {
                try {
                    // 清除相关缓存
                    clearPostCache(postId);
                    log.info("清除缓存成功");
                } catch (Exception e) {
                    log.error("清除缓存失败", e);
                }
            }

            return result;
        }

        log.info("未找到收藏记录");
        return false;
    }

    @Override
    public AppPostFavoritesVO getFavoriteRecord(Long userId, Long postId) {
        Assert.notNull(userId, "用户ID不能为空");
        Assert.notNull(postId, "帖子ID不能为空");

        LambdaQueryWrapper<AppPostFavorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppPostFavorites::getUserId, userId)
                .eq(AppPostFavorites::getPostId, postId)
                .eq(AppPostFavorites::getIsDeleted, 0);

        AppPostFavorites entity = this.getOne(queryWrapper);
        return entity != null ? appPostFavoritesConverter.toVO(entity) : null;
    }

    /**
     * 清除帖子相关的缓存
     */
    private void clearPostCache(Long postId) {
        log.info("开始清除缓存，postId: {}", postId);

        try {
            // 清除帖子列表缓存
            Set<String> pageKeys = redisTemplate.keys(POST_PAGE_KEY + "*");
            if (pageKeys != null && !pageKeys.isEmpty()) {
                redisTemplate.delete(pageKeys);
                log.info("清除帖子列表缓存成功，keys: {}", pageKeys);
            }

            // 清除帖子详情缓存
            String detailKey = POST_DETAIL_KEY + postId;
            redisTemplate.delete(detailKey);
            log.info("清除帖子详情缓存成功，key: {}", detailKey);

            // 清除点赞数缓存
            String likeCountKey = POST_LIKE_COUNT_KEY + postId;
            redisTemplate.delete(likeCountKey);
            log.info("清除点赞数缓存成功，key: {}", likeCountKey);

            // 清除收藏数缓存
            String favoriteCountKey = POST_FAVORITE_COUNT_KEY + postId;
            redisTemplate.delete(favoriteCountKey);
            log.info("清除收藏数缓存成功，key: {}", favoriteCountKey);

        } catch (Exception e) {
            log.error("清除缓存失败", e);
            throw e;
        }
    }
}
