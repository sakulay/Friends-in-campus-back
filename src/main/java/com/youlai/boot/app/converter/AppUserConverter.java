package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppUser;
import com.youlai.boot.app.model.form.AppUserForm;

/**
 * 用户对象转换器
 *
 * @author yuyu
 * @since 2025-02-20 22:03
 */
@Mapper(componentModel = "spring")
public interface AppUserConverter{

    AppUserForm toForm(AppUser entity);

    AppUser toEntity(AppUserForm formData);
}