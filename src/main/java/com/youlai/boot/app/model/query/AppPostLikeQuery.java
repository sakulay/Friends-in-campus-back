package com.youlai.boot.app.model.query;

import com.youlai.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户点赞记录分页查询对象
 *
 * @author yuyu
 * @since 2025-03-01 20:51
 */
@Schema(description ="用户点赞记录查询对象")
@Getter
@Setter
public class AppPostLikeQuery extends BasePageQuery {
    @Schema(description = "点赞用户ID")
    private Long userId;

    @Schema(description = "图文内容ID")
    private Long postId;
}
