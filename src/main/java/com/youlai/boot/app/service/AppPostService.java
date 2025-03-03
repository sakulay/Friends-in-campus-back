package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppPost;
import com.youlai.boot.app.model.form.AppPostForm;
import com.youlai.boot.app.model.query.AppPostQuery;
import com.youlai.boot.app.model.vo.AppPostVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 图文内容服务类
 *
 * @author yuyu
 * @since 2025-03-01 17:11
 */
public interface AppPostService extends IService<AppPost> {

    /**
     *图文内容分页列表
     *
     * @return
     */
    IPage<AppPostVO> getAppPostPage(AppPostQuery queryParams);

    /**
     * 获取图文内容表单数据
     *
     * @param id 图文内容ID
     * @return
     */
     AppPostForm getAppPostFormData(Long id);

    /**
     * 新增图文内容
     *
     * @param formData 图文内容表单对象
     * @return
     */
    boolean saveAppPost(AppPostForm formData);

    /**
     * 修改图文内容
     *
     * @param id   图文内容ID
     * @param formData 图文内容表单对象
     * @return
     */
    boolean updateAppPost(Long id, AppPostForm formData);

    /**
     * 删除图文内容
     *
     * @param ids 图文内容ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppPosts(String ids);

    /**
     * 验证并更新图文内容状态
     * @param id
     * @param status 1 - 审核通过，2 - 拒绝
     * @return
     */
    boolean verifyAppPost(Long id, Integer status);
}
