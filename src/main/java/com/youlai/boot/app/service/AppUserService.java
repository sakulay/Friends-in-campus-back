package com.youlai.boot.app.service;

import com.youlai.boot.app.model.dto.AppUserAuthInfo;
import com.youlai.boot.app.model.entity.AppUser;
import com.youlai.boot.app.model.form.AppUserForm;
import com.youlai.boot.app.model.query.AppUserQuery;
import com.youlai.boot.app.model.vo.AppUserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.boot.system.model.dto.UserAuthInfo;

/**
 * 用户服务类
 *
 * @author yuyu
 * @since 2025-02-20 22:03
 */
public interface AppUserService extends IService<AppUser> {

    /**
     *用户分页列表
     *
     * @return
     */
    IPage<AppUserVO> getAppUserPage(AppUserQuery queryParams);

    /**
     * 获取用户表单数据
     *
     * @param id 用户ID
     * @return
     */
     AppUserForm getAppUserFormData(Long id);

    /**
     * 新增用户
     *
     * @param formData 用户表单对象
     * @return
     */
    boolean saveAppUser(AppUserForm formData);

    /**
     * 修改用户
     *
     * @param id   用户ID
     * @param formData 用户表单对象
     * @return
     */
    boolean updateAppUser(Long id, AppUserForm formData);

    /**
     * 删除用户
     *
     * @param ids 用户ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppUsers(String ids);

    AppUserAuthInfo getUserAuthInfo(String username);
}
