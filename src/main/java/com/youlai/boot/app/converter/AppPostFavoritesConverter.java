package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppPostFavorites;
import com.youlai.boot.app.model.form.AppPostFavoritesForm;
import com.youlai.boot.app.model.vo.AppPostFavoritesVO;

/**
 * 帖子收藏记录对象转换器
 *
 * @author yuyu
 * @since 2025-05-04 12:22
 */
@Mapper(componentModel = "spring")
public interface AppPostFavoritesConverter{

    AppPostFavoritesForm toForm(AppPostFavorites entity);

    AppPostFavorites toEntity(AppPostFavoritesForm formData);

    AppPostFavoritesVO toVO(AppPostFavorites entity);
}