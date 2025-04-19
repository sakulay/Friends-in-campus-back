package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppUserProfile;
import com.youlai.boot.app.model.form.AppUserProfileForm;
import com.youlai.boot.app.model.query.AppUserProfileQuery;
import com.youlai.boot.app.model.vo.AppUserProfileVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户个人信息服务类
 *
 * @author yuyu
 * @since 2025-02-23 19:43
 */
public interface AppUserProfileService extends IService<AppUserProfile> {

    /**
     *用户个人信息分页列表
     *
     * @return
     */
    IPage<AppUserProfileVO> getAppUserProfilePage(AppUserProfileQuery queryParams);

    /**
     * 获取用户个人信息表单数据
     *
     * @param id 用户个人信息ID
     * @return
     */
     AppUserProfileForm getAppUserProfileFormData(Long id);

    /**
     * 获取用户个人简单信息
     * @param studentId
     * @return
     */
     AppUserProfileForm getAppUserSimpleInfo(Long studentId);
    /**
     * 新增用户个人信息
     *
     * @param formData 用户个人信息表单对象
     * @return
     */
    boolean saveAppUserProfile(AppUserProfileForm formData);

    /**
     * 修改用户个人信息
     *
     * @param id   用户个人信息ID
     * @param formData 用户个人信息表单对象
     * @return
     */
    boolean updateAppUserProfile(Long id, AppUserProfileForm formData);

    /**
     * 删除用户个人信息
     *
     * @param ids 用户个人信息ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppUserProfiles(String ids);

    boolean updateAppUserProfileByStudentId(Long studentId, AppUserProfileForm formData);
}
