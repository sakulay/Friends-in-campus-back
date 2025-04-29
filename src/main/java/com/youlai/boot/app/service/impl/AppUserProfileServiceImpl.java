package com.youlai.boot.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.youlai.boot.app.converter.AppUserProfileConverter;
import com.youlai.boot.app.model.mapper.AppUserProfileMapper;
import com.youlai.boot.app.service.AppUserProfileService;
import com.youlai.boot.app.model.entity.AppUserProfile;
import com.youlai.boot.app.model.form.AppUserProfileForm;
import com.youlai.boot.app.model.query.AppUserProfileQuery;
import com.youlai.boot.app.model.vo.AppUserProfileVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 用户个人信息服务实现类
 *
 * @author yuyu
 * @since 2025-02-23 19:43
 */
@Service
@RequiredArgsConstructor
public class AppUserProfileServiceImpl extends ServiceImpl<AppUserProfileMapper, AppUserProfile> implements AppUserProfileService {
    private final AppUserProfileConverter appUserProfileConverter;
    private final AppUserProfileMapper appUserProfileMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String USER_PROFILE_KEY = "app:user:profile:";
    private static final String USER_SIMPLE_INFO_KEY = "app:user:simple:info:";
    private static final String POST_PAGE_KEY = "app:post:page:*";
    private static final String COMMENT_LIST_KEY = "app:post:comment:list:*";
    private static final long CACHE_EXPIRE_TIME = 24; // 缓存过期时间（小时）

    /**
    * 获取用户个人信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppUserProfileVO>} 用户个人信息分页列表
    */
    @Override
    public IPage<AppUserProfileVO> getAppUserProfilePage(AppUserProfileQuery queryParams) {
        Page<AppUserProfileVO> pageVO = this.baseMapper.getAppUserProfilePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }

    /**
     * 获取用户个人信息表单数据
     *
     * @param id 用户个人信息ID
     * @return
     */
    @Override
    public AppUserProfileForm getAppUserProfileFormData(Long id) {
        String cacheKey = USER_PROFILE_KEY + id;
        AppUserProfileForm formData = (AppUserProfileForm) redisTemplate.opsForValue().get(cacheKey);

        if (formData == null) {
            AppUserProfile entity = this.getById(id);
            formData = appUserProfileConverter.toForm(entity);
            // 写入缓存
            redisTemplate.opsForValue().set(cacheKey, formData, CACHE_EXPIRE_TIME, TimeUnit.HOURS);
        }

        return formData;
    }

    @Override
    public AppUserProfileForm getAppUserSimpleInfo(Long studentId) {
        String cacheKey = USER_SIMPLE_INFO_KEY + studentId;
        AppUserProfileForm formData = (AppUserProfileForm) redisTemplate.opsForValue().get(cacheKey);

        if (formData == null) {
            AppUserProfile entity = appUserProfileMapper.getByStudentId(studentId);
            formData = appUserProfileConverter.toForm(entity);
            // 写入缓存
            redisTemplate.opsForValue().set(cacheKey, formData, CACHE_EXPIRE_TIME, TimeUnit.HOURS);
        }

        return formData;
    }

    /**
     * 新增用户个人信息
     *
     * @param formData 用户个人信息表单对象
     * @return
     */
    @Override
    public boolean saveAppUserProfile(AppUserProfileForm formData) {
        // 初始化昵称为"学生xx"
        if(formData.getNickname() == null) formData.setNickname("学生"+formData.getStudentId());
        AppUserProfile entity = appUserProfileConverter.toEntity(formData);
        boolean saved = this.save(entity);
        
        if (saved) {
            // 清除相关缓存
            clearUserCache(formData.getStudentId());
        }
        
        return saved;
    }

    /**
     * 更新用户个人信息
     *
     * @param id   用户个人信息ID
     * @param formData 用户个人信息表单对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAppUserProfile(Long id, AppUserProfileForm formData) {
        formData.setId(id);
        AppUserProfile entity = appUserProfileConverter.toEntity(formData);
        boolean updated = this.updateById(entity);
        
        if (updated) {
            // 清除相关缓存
            clearUserCache(formData.getStudentId());
        }
        
        return updated;
    }

    /**
     * 删除用户个人信息
     *
     * @param ids 用户个人信息ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppUserProfiles(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的用户个人信息数据为空");
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        
        // 获取要删除的用户信息
        List<AppUserProfile> profiles = this.listByIds(idList);
        boolean deleted = this.removeByIds(idList);
        
        if (deleted) {
            // 清除相关缓存
            for (AppUserProfile profile : profiles) {
                clearUserCache(profile.getStudentId());
            }
        }
        
        return deleted;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAppUserProfileByStudentId(Long studentId, AppUserProfileForm formData) {
        LambdaQueryWrapper<AppUserProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AppUserProfile::getStudentId, studentId);
        AppUserProfile userProfile = getOne(wrapper);
        if (userProfile != null) {
            // 更新用户信息
            userProfile.setNickname(formData.getNickname());
            userProfile.setAvatar(formData.getAvatar());
            userProfile.setGender(formData.getGender());
            userProfile.setBio(formData.getBio());
            userProfile.setDeleteUrl(formData.getDeleteUrl());
            boolean updated = updateById(userProfile);
            
            if (updated) {
                // 清除相关缓存
                clearUserCache(studentId);
            }
            
            return updated;
        }
        return false;
    }

    /**
     * 清除用户相关的所有缓存
     */
    private void clearUserCache(Long studentId) {
        // 清除用户个人信息缓存
        String profileKey = USER_PROFILE_KEY + studentId;
        redisTemplate.delete(profileKey);
        
        // 清除用户简单信息缓存
        String simpleInfoKey = USER_SIMPLE_INFO_KEY + studentId;
        redisTemplate.delete(simpleInfoKey);
        
        // 清除帖子列表中的用户信息缓存
        Set<String> postPageKeys = redisTemplate.keys(POST_PAGE_KEY);
        if (postPageKeys != null && !postPageKeys.isEmpty()) {
            redisTemplate.delete(postPageKeys);
        }
        
        // 清除评论列表中的用户信息缓存
        Set<String> commentListKeys = redisTemplate.keys(COMMENT_LIST_KEY);
        if (commentListKeys != null && !commentListKeys.isEmpty()) {
            redisTemplate.delete(commentListKeys);
        }
    }
}
