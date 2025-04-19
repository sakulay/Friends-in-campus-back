package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppPostComment;
import com.youlai.boot.app.model.form.AppPostCommentForm;
import com.youlai.boot.app.model.query.AppPostCommentQuery;
import com.youlai.boot.app.model.vo.AddCommentVO;
import com.youlai.boot.app.model.vo.AppPostCommentVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.boot.app.model.vo.CommentVO;

/**
 * 图文评论服务类
 *
 * @author yuyu
 * @since 2025-03-01 17:24
 */
public interface AppPostCommentService extends IService<AppPostComment> {

    /**
     *图文评论分页列表
     *
     * @return
     */
    IPage<AppPostCommentVO> getAppPostCommentPage(AppPostCommentQuery queryParams);

    /**
     * 获取图文评论表单数据
     *
     * @param id 图文评论ID
     * @return
     */
     AppPostCommentForm getAppPostCommentFormData(Long id);

    /**
     * 新增图文评论
     *
     * @param formData 图文评论表单对象
     * @return
     */
    AddCommentVO saveAppPostComment(AppPostCommentForm formData);

    /**
     * 修改图文评论
     *
     * @param id   图文评论ID
     * @param formData 图文评论表单对象
     * @return
     */
    boolean updateAppPostComment(Long id, AppPostCommentForm formData);

    /**
     * 删除图文评论
     *
     * @param ids 图文评论ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppPostComments(String ids);

    /**
     * 根据图文Id获取对应的评论列表
     * @param queryParams
     * @return
     */
    IPage<CommentVO> getCommentPageById(AppPostCommentQuery queryParams);
}
