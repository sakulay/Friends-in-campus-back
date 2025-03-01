package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppChatMessage;
import com.youlai.boot.app.model.form.AppChatMessageForm;

/**
 * 用户聊天记录对象转换器
 *
 * @author yuyu
 * @since 2025-02-28 18:44
 */
@Mapper(componentModel = "spring")
public interface AppChatMessageConverter{

    AppChatMessageForm toForm(AppChatMessage entity);

    AppChatMessage toEntity(AppChatMessageForm formData);
}