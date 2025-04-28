package com.youlai.boot.app.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppNews;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppNewsQuery;
import com.youlai.boot.app.model.vo.AppNewsVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 校园新闻资讯Mapper接口
 *
 * @author yuyu
 * @since 2025-04-20 08:10
 */
@Mapper
public interface AppNewsMapper extends BaseMapper<AppNews> {

    /**
     * 获取校园新闻资讯分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppNewsVO> getAppNewsPage(Page<AppNewsVO> page, AppNewsQuery queryParams);

}
