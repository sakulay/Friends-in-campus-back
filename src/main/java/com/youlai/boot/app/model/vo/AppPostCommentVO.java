package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 图文评论视图对象
 *
 * @author yuyu
 * @since 2025-03-01 17:24
 */
@Getter
@Setter
@Schema( description = "图文评论视图对象")
public class AppPostCommentVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;
    @Schema(description = "评论用户ID，关联到 app_user 表")
    private Long userId;
    @Schema(description = "图文内容ID，关联到 app_post 表")
    private Long postId;
    @Schema(description = "评论内容")
    private String content;
    @Schema(description = "回复的评论ID，NULL表示主评论")
    private Integer parentId;
    @Schema(description = "评论时间")
    private LocalDateTime createTime;
    @Schema(description = "是否删除(1-删除，0-未删除)")
    private Integer isDeleted;
    @Schema(description = "是否为回复评论(0为评论，1为回复)")
    private Integer isReply;
    @Schema(description = "审核状态（0 - 待审核，1 - 已通过， 2 - 禁用）")
    private Integer status;
}
