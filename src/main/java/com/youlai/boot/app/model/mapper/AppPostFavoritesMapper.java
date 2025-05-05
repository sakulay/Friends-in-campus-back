package com.youlai.boot.app.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppPostFavorites;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppPostFavoritesQuery;
import com.youlai.boot.app.model.vo.AppPostFavoritesVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 帖子收藏记录Mapper接口
 *
 * @author yuyu
 * @since 2025-05-04 12:22
 */
@Mapper
public interface AppPostFavoritesMapper extends BaseMapper<AppPostFavorites> {

    /**
     * 获取帖子收藏记录分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppPostFavoritesVO> getAppPostFavoritesPage(Page<AppPostFavoritesVO> page, AppPostFavoritesQuery queryParams);

}
