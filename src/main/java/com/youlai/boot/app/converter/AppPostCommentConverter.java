package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppPostComment;
import com.youlai.boot.app.model.form.AppPostCommentForm;

/**
 * 图文评论对象转换器
 *
 * @author yuyu
 * @since 2025-03-01 17:24
 */
@Mapper(componentModel = "spring")
public interface AppPostCommentConverter{

    AppPostCommentForm toForm(AppPostComment entity);

    AppPostComment toEntity(AppPostCommentForm formData);
}