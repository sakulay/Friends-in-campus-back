package com.youlai.boot.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.model.mapper.AppPostLikeMapper;
import com.youlai.boot.app.service.AppPostLikeService;
import com.youlai.boot.app.model.entity.AppPostLike;
import com.youlai.boot.app.model.form.AppPostLikeForm;
import com.youlai.boot.app.model.query.AppPostLikeQuery;
import com.youlai.boot.app.model.vo.AppPostLikeVO;
import com.youlai.boot.app.converter.AppPostLikeConverter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户点赞记录服务实现类
 *
 * @author yuyu
 * @since 2025-03-01 20:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AppPostLikeServiceImpl extends ServiceImpl<AppPostLikeMapper, AppPostLike> implements AppPostLikeService {

    private final AppPostLikeConverter appPostLikeConverter;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String POST_PAGE_KEY = "app:post:page:";
    private static final String POST_DETAIL_KEY = "app:post:detail:";
    private static final String POST_LIKE_COUNT_KEY = "app:post:like:count:";
    private static final String POST_FAVORITE_COUNT_KEY = "app:post:favorite:count:";
    private static final long CACHE_EXPIRE_TIME = 24; // 缓存过期时间（小时）

    /**
    * 获取用户点赞记录分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppPostLikeVO>} 用户点赞记录分页列表
    */
    @Override
    public IPage<AppPostLikeVO> getAppPostLikePage(AppPostLikeQuery queryParams) {
        Page<AppPostLikeVO> pageVO = this.baseMapper.getAppPostLikePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取用户点赞记录表单数据
     *
     * @param id 用户点赞记录ID
     * @return
     */
    @Override
    public AppPostLikeForm getAppPostLikeFormData(Long id) {
        AppPostLike entity = this.getById(id);
        return appPostLikeConverter.toForm(entity);
    }
    
    /**
     * 新增用户点赞记录
     *
     * @param formData 用户点赞记录表单对象
     * @return
     */
    @Override
    public boolean saveAppPostLike(AppPostLikeForm formData) {
        // 检查是否已经点赞
        AppPostLike existingLike = this.getOne(
            new LambdaQueryWrapper<AppPostLike>()
                .eq(AppPostLike::getUserId, formData.getUserId())
                .eq(AppPostLike::getPostId, formData.getPostId())
                .eq(AppPostLike::getIsDeleted, 0)
        );

        boolean result;
        if (existingLike != null) {
            // 如果已经点赞，则取消点赞（逻辑删除）
//            existingLike.setIsDeleted(1);
//            result = this.updateById(existingLike);
            return false;
        } else {
            // 如果未点赞，则新增点赞记录
            AppPostLike entity = appPostLikeConverter.toEntity(formData);
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
     * 更新用户点赞记录
     *
     * @param id   用户点赞记录ID
     * @param formData 用户点赞记录表单对象
     * @return
     */
    @Override
    public boolean updateAppPostLike(Long id,AppPostLikeForm formData) {
        AppPostLike entity = appPostLikeConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除用户点赞记录
     *
     * @param ids 用户点赞记录ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppPostLikes(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的用户点赞记录数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelLike(Long userId, Long postId) {
        log.info("开始取消点赞，userId: {}, postId: {}", userId, postId);
        
        // 查找点赞记录
        AppPostLike existingLike = this.getOne(
            new LambdaQueryWrapper<AppPostLike>()
                .eq(AppPostLike::getUserId, userId)
                .eq(AppPostLike::getPostId, postId)
                .eq(AppPostLike::getIsDeleted, 0)
        );

        if (existingLike != null) {
            log.info("找到点赞记录，id: {}", existingLike.getId());
            
            // 使用 LambdaUpdateWrapper 进行更新
            boolean result = this.update(
                new LambdaUpdateWrapper<AppPostLike>()
                    .eq(AppPostLike::getId, existingLike.getId())
                    .eq(AppPostLike::getIsDeleted, 0)
                    .set(AppPostLike::getIsDeleted, 1)
            );
            
            log.info("更新点赞记录结果: {}", result);
            
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
        
        log.info("未找到点赞记录");
        return false;
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
