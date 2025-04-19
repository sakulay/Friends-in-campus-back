package com.youlai.boot.app.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppPostLike;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppPostLikeQuery;
import com.youlai.boot.app.model.vo.AppPostLikeVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户点赞记录Mapper接口
 *
 * @author yuyu
 * @since 2025-03-01 20:51
 */
@Mapper
public interface AppPostLikeMapper extends BaseMapper<AppPostLike> {

    /**
     * 获取用户点赞记录分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppPostLikeVO> getAppPostLikePage(Page<AppPostLikeVO> page, AppPostLikeQuery queryParams);

}
