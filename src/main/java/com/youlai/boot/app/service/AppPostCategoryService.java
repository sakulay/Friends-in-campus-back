package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppPostCategory;
import com.youlai.boot.app.model.form.AppPostCategoryForm;
import com.youlai.boot.app.model.query.AppPostCategoryQuery;
import com.youlai.boot.app.model.vo.AppPostCategoryVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 帖子分类服务类
 *
 * @author yuyu
 * @since 2025-05-14 13:00
 */
public interface AppPostCategoryService extends IService<AppPostCategory> {

    /**
     *帖子分类分页列表
     *
     * @return
     */
    IPage<AppPostCategoryVO> getAppPostCategoryPage(AppPostCategoryQuery queryParams);

    /**
     * 获取帖子分类表单数据
     *
     * @param id 帖子分类ID
     * @return
     */
     AppPostCategoryForm getAppPostCategoryFormData(Long id);

    /**
     * 新增帖子分类
     *
     * @param formData 帖子分类表单对象
     * @return
     */
    boolean saveAppPostCategory(AppPostCategoryForm formData);

    /**
     * 修改帖子分类
     *
     * @param id   帖子分类ID
     * @param formData 帖子分类表单对象
     * @return
     */
    boolean updateAppPostCategory(Long id, AppPostCategoryForm formData);

    /**
     * 删除帖子分类
     *
     * @param ids 帖子分类ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppPostCategorys(String ids);

}
