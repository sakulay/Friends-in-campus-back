package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppPostBrowseRecord;
import com.youlai.boot.app.model.form.AppPostBrowseRecordForm;

/**
 * 帖子浏览记录对象转换器
 *
 * @author yuyu
 * @since 2025-05-03 12:51
 */
@Mapper(componentModel = "spring")
public interface AppPostBrowseRecordConverter{

    AppPostBrowseRecordForm toForm(AppPostBrowseRecord entity);

    AppPostBrowseRecord toEntity(AppPostBrowseRecordForm formData);
}