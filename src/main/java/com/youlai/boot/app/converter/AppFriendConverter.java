package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppFriend;
import com.youlai.boot.app.model.form.AppFriendForm;

/**
 * 用户好友关系对象转换器
 *
 * @author yuyu
 * @since 2025-02-28 18:25
 */
@Mapper(componentModel = "spring")
public interface AppFriendConverter{

    AppFriendForm toForm(AppFriend entity);

    AppFriend toEntity(AppFriendForm formData);
}