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
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
        AppUserProfile entity = this.getById(id);
        return appUserProfileConverter.toForm(entity);
    }

    @Override
    public AppUserProfileForm getAppUserSimpleInfo(Long studentId) {
        AppUserProfile entity = appUserProfileMapper.getByStudentId(studentId);
        return appUserProfileConverter.toForm(entity);
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
        return this.save(entity);
    }

    /**
     * 更新用户个人信息
     *
     * @param id   用户个人信息ID
     * @param formData 用户个人信息表单对象
     * @return
     */
    @Override
    public boolean updateAppUserProfile(Long id,AppUserProfileForm formData) {
        formData.setId(id);
        AppUserProfile entity = appUserProfileConverter.toEntity(formData);
        return this.updateById(entity);
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
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    @Override
    public boolean updateAppUserProfileByStudentId(Long studentId, AppUserProfileForm formData) {
        LambdaQueryWrapper<AppUserProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AppUserProfile::getStudentId, studentId);
        AppUserProfile userProfile = getOne(wrapper);
        if (userProfile != null) {
            // 将formData中的数据复制到userProfile对象中
            // 这里假设有一个工具类或方法来完成这个复制操作
            // BeanUtils.copyProperties(formData, userProfile);
            // 或者手动复制属性
            userProfile.setNickname(formData.getNickname());
            userProfile.setAvatar(formData.getAvatar());
            userProfile.setGender(formData.getGender());
            userProfile.setBio(formData.getBio());
            userProfile.setDeleteUrl(formData.getDeleteUrl());
            // ... 其他属性的复制 ...
            return updateById(userProfile);
        }
        return false;
    }
}
