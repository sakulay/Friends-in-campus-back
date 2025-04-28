package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppNews;
import com.youlai.boot.app.model.form.AppNewsForm;

/**
 * 校园新闻资讯对象转换器
 *
 * @author yuyu
 * @since 2025-04-20 08:10
 */
@Mapper(componentModel = "spring")
public interface AppNewsConverter{

    AppNewsForm toForm(AppNews entity);

    AppNews toEntity(AppNewsForm formData);
}