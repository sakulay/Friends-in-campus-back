package com.youlai.boot.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppPostImage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppPostImageQuery;
import com.youlai.boot.app.model.vo.AppPostImageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图文图片Mapper接口
 *
 * @author yuyu
 * @since 2025-03-01 19:27
 */
@Mapper
public interface AppPostImageMapper extends BaseMapper<AppPostImage> {

    /**
     * 获取图文图片分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppPostImageVO> getAppPostImagePage(Page<AppPostImageVO> page, AppPostImageQuery queryParams);

}
