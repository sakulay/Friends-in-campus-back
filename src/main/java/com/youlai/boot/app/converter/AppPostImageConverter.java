package com.youlai.boot.app.converter;

import com.youlai.boot.app.model.vo.AppPostImageVO;
import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppPostImage;
import com.youlai.boot.app.model.form.AppPostImageForm;

/**
 * 图文图片对象转换器
 *
 * @author yuyu
 * @since 2025-03-01 19:27
 */
@Mapper(componentModel = "spring")
public interface AppPostImageConverter{

    AppPostImageForm toForm(AppPostImage entity);

    AppPostImage toEntity(AppPostImageForm formData);

    AppPostImageVO toVOList(AppPostImage entity);
}