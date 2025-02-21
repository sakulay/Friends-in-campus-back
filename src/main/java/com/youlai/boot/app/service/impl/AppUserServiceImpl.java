package com.youlai.boot.app.service.impl;

import com.youlai.boot.app.model.dto.AppUserAuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.app.mapper.AppUserMapper;
import com.youlai.boot.app.service.AppUserService;
import com.youlai.boot.app.model.entity.AppUser;
import com.youlai.boot.app.model.form.AppUserForm;
import com.youlai.boot.app.model.query.AppUserQuery;
import com.youlai.boot.app.model.vo.AppUserVO;
import com.youlai.boot.app.converter.AppUserConverter;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 用户服务实现类
 *
 * @author yuyu
 * @since 2025-02-20 22:03
 */
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements AppUserService {

    private final AppUserConverter appUserConverter;

    /**
    * 获取用户分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AppUserVO>} 用户分页列表
    */
    @Override
    public IPage<AppUserVO> getAppUserPage(AppUserQuery queryParams) {
        Page<AppUserVO> pageVO = this.baseMapper.getAppUserPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取用户表单数据
     *
     * @param id 用户ID
     * @return
     */
    @Override
    public AppUserForm getAppUserFormData(Long id) {
        AppUser entity = this.getById(id);
        return appUserConverter.toForm(entity);
    }
    
    /**
     * 新增用户
     *
     * @param formData 用户表单对象
     * @return
     */
    @Override
    public boolean saveAppUser(AppUserForm formData) {
        AppUser entity = appUserConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新用户
     *
     * @param id   用户ID
     * @param formData 用户表单对象
     * @return
     */
    @Override
    public boolean updateAppUser(Long id,AppUserForm formData) {
        AppUser entity = appUserConverter.toEntity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除用户
     *
     * @param ids 用户ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteAppUsers(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的用户数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    @Override
    public AppUserAuthInfo getUserAuthInfo(String username) {
        AppUserAuthInfo userAuthInfo = this.baseMapper.getUserAuthInfo(username);
        String password = userAuthInfo.getPassword();
        password = new BCryptPasswordEncoder().encode(password);
        userAuthInfo.setPassword(password);
        return userAuthInfo;
    }

}
