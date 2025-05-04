package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppPostLike;
import com.youlai.boot.app.model.form.AppPostLikeForm;
import com.youlai.boot.app.model.query.AppPostLikeQuery;
import com.youlai.boot.app.model.vo.AppPostLikeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户点赞记录服务类
 *
 * @author yuyu
 * @since 2025-03-01 20:51
 */
public interface AppPostLikeService extends IService<AppPostLike> {

    /**
     *用户点赞记录分页列表
     *
     * @return
     */
    IPage<AppPostLikeVO> getAppPostLikePage(AppPostLikeQuery queryParams);

    /**
     * 获取用户点赞记录表单数据
     *
     * @param id 用户点赞记录ID
     * @return
     */
     AppPostLikeForm getAppPostLikeFormData(Long id);

    /**
     * 新增用户点赞记录
     *
     * @param formData 用户点赞记录表单对象
     * @return
     */
    boolean saveAppPostLike(AppPostLikeForm formData);

    /**
     * 修改用户点赞记录
     *
     * @param id   用户点赞记录ID
     * @param formData 用户点赞记录表单对象
     * @return
     */
    boolean updateAppPostLike(Long id, AppPostLikeForm formData);

    /**
     * 删除用户点赞记录
     *
     * @param ids 用户点赞记录ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppPostLikes(String ids);

    /**
     * 取消点赞
     *
     * @param userId 用户ID
     * @param postId 帖子ID
     * @return
     */
    boolean cancelLike(Long userId, Long postId);
}
