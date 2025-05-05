package com.youlai.boot.app.service;

import com.youlai.boot.app.model.entity.AppPostFavorites;
import com.youlai.boot.app.model.form.AppPostFavoritesForm;
import com.youlai.boot.app.model.query.AppPostFavoritesQuery;
import com.youlai.boot.app.model.vo.AppPostFavoritesVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.boot.app.model.vo.AppPostVO;

/**
 * 帖子收藏记录服务类
 *
 * @author yuyu
 * @since 2025-05-04 12:22
 */
public interface AppPostFavoritesService extends IService<AppPostFavorites> {

    /**
     *帖子收藏记录分页列表
     *
     * @return
     */
    IPage<AppPostVO> getAppPostFavoritesPage(AppPostFavoritesQuery queryParams);

    /**
     * 获取帖子收藏记录表单数据
     *
     * @param id 帖子收藏记录ID
     * @return
     */
     AppPostFavoritesForm getAppPostFavoritesFormData(Long id);

    /**
     * 新增帖子收藏记录
     *
     * @param formData 帖子收藏记录表单对象
     * @return
     */
    boolean saveAppPostFavorites(AppPostFavoritesForm formData);

    /**
     * 修改帖子收藏记录
     *
     * @param id   帖子收藏记录ID
     * @param formData 帖子收藏记录表单对象
     * @return
     */
    boolean updateAppPostFavorites(Long id, AppPostFavoritesForm formData);

    /**
     * 删除帖子收藏记录
     *
     * @param ids 帖子收藏记录ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteAppPostFavoritess(String ids);

    /**
     * 取消收藏
     *
     * @param userId 用户ID
     * @param postId 帖子ID
     * @return 是否成功
     */
    boolean cancelFavorite(Long userId, Long postId);

    /**
     * 查询收藏记录
     *
     * @param userId 用户ID
     * @param postId 帖子ID
     * @return 收藏记录
     */
    AppPostFavoritesVO getFavoriteRecord(Long userId, Long postId);

}
