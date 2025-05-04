package com.youlai.boot.app.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppPost;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppPostQuery;
import com.youlai.boot.app.model.vo.AppPostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 图文内容Mapper接口
 *
 * @author yuyu
 * @since 2025-03-01 17:11
 */
@Mapper
public interface AppPostMapper extends BaseMapper<AppPost> {

    /**
     * 获取图文内容分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppPostVO> getAppPostPage(Page<AppPostVO> page, AppPostQuery queryParams);

    /**
     * 调试方法：获取SQL执行情况
     *
     * @param queryParams 查询参数
     * @return
     */
    @Select("SELECT * FROM app_post WHERE id IN (${postIds}) ORDER BY FIELD(id, ${postIds})")
    List<AppPostVO> debugGetPosts(@Param("postIds") String postIds);

}
