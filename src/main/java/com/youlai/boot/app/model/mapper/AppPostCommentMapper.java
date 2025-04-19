package com.youlai.boot.app.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.app.model.entity.AppPostComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.boot.app.model.query.AppPostCommentQuery;
import com.youlai.boot.app.model.vo.AppPostCommentVO;
import com.youlai.boot.app.model.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 图文评论Mapper接口
 *
 * @author yuyu
 * @since 2025-03-01 17:24
 */
@Mapper
public interface AppPostCommentMapper extends BaseMapper<AppPostComment> {

    /**
     * 获取图文评论分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<AppPostCommentVO> getAppPostCommentPage(Page<AppPostCommentVO> page, AppPostCommentQuery queryParams);

    List<CommentVO> getCommentPageById(int postId);
}
