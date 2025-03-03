package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppPostLike;
import com.youlai.boot.app.model.form.AppPostLikeForm;

/**
 * 用户点赞记录对象转换器
 *
 * @author yuyu
 * @since 2025-03-01 20:51
 */
@Mapper(componentModel = "spring")
public interface AppPostLikeConverter{

    AppPostLikeForm toForm(AppPostLike entity);

    AppPostLike toEntity(AppPostLikeForm formData);
}