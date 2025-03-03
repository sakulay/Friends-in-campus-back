package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppPostImage;
import com.youlai.boot.app.model.form.AppPostImageForm;
import com.youlai.boot.app.model.query.AppPostImageQuery;
import com.youlai.boot.app.model.vo.AppPostImageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 图文图片服务类
 *
 * @author yuyu
 * @since 2025-03-01 19:27
 */
public interface AppPostImageService extends IService<AppPostImage> {

    /**
     *图文图片分页列表
     *
     * @return
     */
    IPage<AppPostImageVO> getAppPostImagePage(AppPostImageQuery queryParams);

    /**
     * 获取图文图片表单数据
     *
     * @param id 图文图片ID
     * @return
     */
     AppPostImageForm getAppPostImageFormData(Long id);

    /**
     * 新增图文图片
     *
     * @param formData 图文图片表单对象
     * @return
     */
    boolean saveAppPostImage(AppPostImageForm formData);

    /**
     * 修改图文图片
     *
     * @param id   图文图片ID
     * @param formData 图文图片表单对象
     * @return
     */
    boolean updateAppPostImage(Long id, AppPostImageForm formData);

    /**
     * 删除图文图片
     *
     * @param ids 图文图片ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppPostImages(String ids);

}
