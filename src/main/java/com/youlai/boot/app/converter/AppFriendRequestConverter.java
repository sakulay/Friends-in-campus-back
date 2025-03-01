package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppFriendRequest;
import com.youlai.boot.app.model.form.AppFriendRequestForm;

/**
 * 好友申请对象转换器
 *
 * @author yuyu
 * @since 2025-02-28 18:38
 */
@Mapper(componentModel = "spring")
public interface AppFriendRequestConverter{

    AppFriendRequestForm toForm(AppFriendRequest entity);

    AppFriendRequest toEntity(AppFriendRequestForm formData);
}