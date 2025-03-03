package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppPost;
import com.youlai.boot.app.model.form.AppPostForm;

/**
 * 图文内容对象转换器
 *
 * @author yuyu
 * @since 2025-03-01 17:11
 */
@Mapper(componentModel = "spring")
public interface AppPostConverter{

    AppPostForm toForm(AppPost entity);

    AppPost toEntity(AppPostForm formData);
}