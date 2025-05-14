package com.youlai.boot.app.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppPostCategory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppPostCategoryQuery;
import com.youlai.boot.app.model.vo.AppPostCategoryVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 帖子分类Mapper接口
 *
 * @author yuyu
 * @since 2025-05-14 13:00
 */
@Mapper
public interface AppPostCategoryMapper extends BaseMapper<AppPostCategory> {

    /**
     * 获取帖子分类分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppPostCategoryVO> getAppPostCategoryPage(Page<AppPostCategoryVO> page, AppPostCategoryQuery queryParams);

}
