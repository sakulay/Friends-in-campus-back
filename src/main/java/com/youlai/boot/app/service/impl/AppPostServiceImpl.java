package com.youlai.boot.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.boot.app.converter.AppPostImageConverter;
import com.youlai.boot.app.model.mapper.AppPostCommentMapper;
import com.youlai.boot.app.model.mapper.AppPostImageMapper;
import com.youlai.boot.app.model.mapper.AppPostLikeMapper;
import com.youlai.boot.app.model.mapper.AppPostFavoritesMapper;
import com.youlai.boot.app.model.entity.AppPostComment;
import com.youlai.boot.app.model.entity.AppPostImage;
import com.youlai.boot.app.model.entity.AppPostLike;
import com.youlai.boot.app.model.entity.AppPostFavorites;
import com.youlai.boot.app.model.form.AppPostImageForm;
import com.youlai.boot.app.model.vo.AppPostImageVO;
import com.youlai.boot.app.model.vo.FriendSimpleVO;
import com.youlai.boot.app.service.AppFriendService;
import com.youlai.boot.app.service.AppPostImageService;
import com.youlai.boot.core.security.model.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.model.mapper.AppPostMapper;
import com.youlai.boot.app.service.AppPostService;
import com.youlai.boot.app.model.entity.AppPost;
import com.youlai.boot.app.model.form.AppPostForm;
import com.youlai.boot.app.model.query.AppPostQuery;
import com.youlai.boot.app.model.vo.AppPostVO;
import com.youlai.boot.app.converter.AppPostConverter;
import com.youlai.boot.core.security.manager.AppJwtTokenManger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 图文内容服务实现类
 *
 * @author yuyu
 * @since 2025-03-01 17:11
 */
@Service
@RequiredArgsConstructor
public class AppPostServiceImpl extends ServiceImpl<AppPostMapper, AppPost> implements AppPostService {

    private final AppPostConverter appPostConverter;
    private final AppPostCommentMapper appPostCommentMapper;
    private final AppPostLikeMapper appPostLikeMapper;
    private final AppPostImageMapper appPostImageMapper;
    private final AppPostImageConverter appPostImageConverter;
    private final AppPostImageService appPostImageService;
    private final AppFriendService appFriendService;
    private final AppJwtTokenManger appJwtTokenManger;
    private final AppPostFavoritesMapper appPostFavoritesMapper;

    /**
    * 获取图文内容分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppPostVO>} 图文内容分页列表
    */
    @Override
    @Transactional
    public IPage<AppPostVO> getAppPostPage(AppPostQuery queryParams) {
        IPage<AppPostVO> pageVO = this.baseMapper.getAppPostPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 获取所有的帖子ID
        List<Long> postIds = pageVO.getRecords().stream()
                .map(AppPostVO::getId)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(postIds)) {
            return pageVO;
        }

        // 获取所有的用户ID
        List<Long> userIds = pageVO.getRecords().stream()
                .map(AppPostVO::getUserId)
                .collect(Collectors.toList());

        // 根据用户ID查询friendSimpleVO
        Map<Long, FriendSimpleVO> friendInfoMap = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AppUserDetails) {
            AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
            for (Long userId : userIds) {
                FriendSimpleVO friendSimpleVO = appFriendService.getAppFriendInfo(Long.parseLong(userDetails.getStudentId()), userId);
                friendInfoMap.put(userId, friendSimpleVO);
            }
        }

        // 查询点赞数
        Map<Long, Integer> likeCountMap = postIds.stream()
                .collect(Collectors.toMap(
                        postId -> postId,
                        postId -> appPostLikeMapper.selectCount(
                                new LambdaQueryWrapper<AppPostLike>()
                                        .eq(AppPostLike::getPostId, postId)
                                        .eq(AppPostLike::getIsDeleted, 0)
                        ).intValue(),
                        (v1, v2) -> v1
                ));

        // 查询评论数
        Map<Long, Integer> commentCountMap = postIds.stream()
                .collect(Collectors.toMap(
                        postId -> postId,
                        postId -> appPostCommentMapper.selectCount(
                                new LambdaQueryWrapper<AppPostComment>()
                                        .eq(AppPostComment::getPostId, postId)
                                        .eq(AppPostComment::getIsDeleted, 0)
                                        .eq(AppPostComment::getStatus, 1)
                        ).intValue(),
                        (v1, v2) -> v1
                ));

        // 查询收藏数
        Map<Long, Integer> favoriteCountMap = postIds.stream()
                .collect(Collectors.toMap(
                        postId -> postId,
                        postId -> appPostFavoritesMapper.selectCount(
                                new LambdaQueryWrapper<AppPostFavorites>()
                                        .eq(AppPostFavorites::getPostId, postId)
                                        .eq(AppPostFavorites::getIsDeleted, 0)
                        ).intValue(),
                        (v1, v2) -> v1
                ));

        // 查询图片列表
        Map<Long, List<AppPostImageVO>> imageListMap = appPostImageMapper.selectList(
                        new LambdaQueryWrapper<AppPostImage>()
                                .in(AppPostImage::getPostId, postIds)
                                .eq(AppPostImage::getIsDeleted, 0)
                ).stream().map(appPostImageConverter::toVOList)
                .collect(Collectors.groupingBy(AppPostImageVO::getPostId));

        // 组装数据
        pageVO.getRecords().forEach(post -> {
            post.setLikeCount(likeCountMap.getOrDefault(post.getId(), 0));
            post.setCommentCount(commentCountMap.getOrDefault(post.getId(), 0));
            post.setFavoriteCount(favoriteCountMap.getOrDefault(post.getId(), 0));
            post.setImageList(imageListMap.getOrDefault(post.getId(), Collections.emptyList()));
            post.setUserInfo(friendInfoMap.get(post.getUserId()));
        });
            // if(queryParams.getPostIds() == null) {
            //     // 写入缓存
            //     redisTemplate.opsForValue().set(cacheKey, pageVO, CACHE_EXPIRE_TIME, TimeUnit.HOURS);
            //

        return pageVO;
    }
    
    /**
     * 获取图文内容表单数据
     *
     * @param id 图文内容ID
     * @return
     */
    @Override
    public AppPostForm getAppPostFormData(Long id) {
        AppPost entity = this.getById(id);
        return appPostConverter.toForm(entity);
    }
    
    /**
     * 新增图文内容
     *
     * @param formData 图文内容表单对象
     * @return
     */
    @Override
    @Transactional
    public boolean saveAppPost(AppPostForm formData) {
        // 1. 先将图文的文字内容保存到 app_post 表中
        AppPost entity = appPostConverter.toEntity(formData);
        boolean saved = this.save(entity);
        // 2. 将图片列表保存到 app_post_image 表中
        if (saved && formData.getImageList() != null) {
            AtomicInteger order = new AtomicInteger();
            List<AppPostImage> appPostImages = formData.getImageList().stream()
                    .map(form -> {
                        AppPostImage image = appPostImageConverter.toEntity(form);
                        image.setPostId(entity.getId());
                        image.setImageOrder(order.getAndIncrement());
                        return image;
                    })
                    .collect(Collectors.toList());
            appPostImageService.saveBatch(appPostImages);
        }

        return saved;
    }
    
    /**
     * 更新图文内容
     *
     * @param id   图文内容ID
     * @param formData 图文内容表单对象
     * @return
     */
    @Override
    @Transactional
    public boolean updateAppPost(Long id, AppPostForm formData) {
        AppPost entity = appPostConverter.toEntity(formData);
        entity.setId(id);
        boolean updated = this.updateById(entity);

        if (updated) {
            // 处理图片
            List<AppPostImage> existingImages = appPostImageMapper.selectList(
                    new LambdaQueryWrapper<AppPostImage>()
                            .eq(AppPostImage::getPostId, id)
                            .eq(AppPostImage::getIsDeleted, 0)
            );

            List<Long> existingImageIds = existingImages.stream()
                    .map(AppPostImage::getId).toList();

            List<Long> newImageIds = formData.getImageList().stream()
                    .map(AppPostImageForm::getId)
                    .filter(Objects::nonNull).toList();

            // 找出需要删除的图片
            List<Long> imagesToDelete = existingImageIds.stream()
                    .filter(id1 -> !newImageIds.contains(id1))
                    .collect(Collectors.toList());

            // 找出需要新增的图片
            List<AppPostImageForm> imagesToAdd = formData.getImageList().stream()
                    .filter(form -> form.getId() == null).toList();

            // 删除不再需要的图片
            if (!imagesToDelete.isEmpty()) {
                appPostImageMapper.deleteBatchIds(imagesToDelete);
            }

            // 添加新的图片
            if (!imagesToAdd.isEmpty()) {
                List<AppPostImage> newImages = imagesToAdd.stream()
                        .map(form -> {
                            AppPostImage image = appPostImageConverter.toEntity(form);
                            image.setPostId(id);
                            return image;
                        })
                        .collect(Collectors.toList());
                appPostImageService.saveBatch(newImages);
            }
        }

        return true;
    }
    
    /**
     * 删除图文内容
     *
     * @param ids 图文内容ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    @Transactional
    public boolean deleteAppPosts(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的图文内容数据为空");
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
    
        // 删除对应的图片
        appPostImageMapper.delete(new LambdaQueryWrapper<AppPostImage>().in(AppPostImage::getPostId, idList));
        
        return this.removeByIds(idList);
    }

    /**
     * 验证并更新图文内容状态
     * @param id
     * @param status 1 - 审核通过，2 - 拒绝
     * @return
     */
    @Override
    @Transactional
    public boolean verifyAppPost(Long id, Integer status) {
        // status = 1 通过，2 拒绝
        AppPost entity = this.getById(id);
        if (entity == null) {
            return false;
        }
        entity.setStatus(status);
        return this.updateById(entity);
    }
}
