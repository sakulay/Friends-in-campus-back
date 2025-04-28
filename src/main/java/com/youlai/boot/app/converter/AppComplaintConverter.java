package com.youlai.boot.app.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.entity.AppComplaint;
import com.youlai.boot.app.model.form.AppComplaintForm;

/**
 * 用户举报记录对象转换器
 *
 * @author yuyu
 * @since 2025-04-22 20:49
 */
@Mapper(componentModel = "spring")
public interface AppComplaintConverter{

    AppComplaintForm toForm(AppComplaint entity);

    AppComplaint toEntity(AppComplaintForm formData);
}