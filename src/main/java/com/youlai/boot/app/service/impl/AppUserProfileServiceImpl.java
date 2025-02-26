package com.youlai.boot.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.mapper.AppUserProfileMapper;
import com.youlai.boot.app.service.AppUserProfileService;
import com.youlai.boot.app.model.entity.AppUserProfile;
import com.youlai.boot.app.model.form.AppUserProfileForm;
import com.youlai.boot.app.model.query.AppUserProfileQuery;
import com.youlai.boot.app.model.vo.AppUserProfileVO;
import com.youlai.boot.app.converter.AppUserProfileConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

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

}
