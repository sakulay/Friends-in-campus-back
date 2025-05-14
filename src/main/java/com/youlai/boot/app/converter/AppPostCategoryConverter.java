package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppPostCategory;
import com.youlai.boot.app.model.form.AppPostCategoryForm;

/**
 * 帖子分类对象转换器
 *
 * @author yuyu
 * @since 2025-05-14 13:00
 */
@Mapper(componentModel = "spring")
public interface AppPostCategoryConverter{

    AppPostCategoryForm toForm(AppPostCategory entity);

    AppPostCategory toEntity(AppPostCategoryForm formData);
}