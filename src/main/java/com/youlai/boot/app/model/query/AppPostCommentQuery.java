package com.youlai.boot.app.model.query;

import com.youlai.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 图文评论分页查询对象
 *
 * @author yuyu
 * @since 2025-03-01 17:24
 */
@Schema(description ="图文评论查询对象")
@Getter
@Setter
public class AppPostCommentQuery extends BasePageQuery {
    @Schema(description = "图文ID")
    int postId;
}
