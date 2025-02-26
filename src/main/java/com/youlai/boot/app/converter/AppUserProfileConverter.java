package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppUserProfile;
import com.youlai.boot.app.model.form.AppUserProfileForm;

/**
 * 用户个人信息对象转换器
 *
 * @author yuyu
 * @since 2025-02-23 19:43
 */
@Mapper(componentModel = "spring")
public interface AppUserProfileConverter{

    AppUserProfileForm toForm(AppUserProfile entity);

    AppUserProfile toEntity(AppUserProfileForm formData);
}