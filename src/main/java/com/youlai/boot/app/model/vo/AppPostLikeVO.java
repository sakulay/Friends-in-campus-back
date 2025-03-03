package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 用户点赞记录视图对象
 *
 * @author yuyu
 * @since 2025-03-01 20:51
 */
@Getter
@Setter
@Schema( description = "用户点赞记录视图对象")
public class AppPostLikeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;
    @Schema(description = "点赞用户ID，关联到 app_user 表")
    private Long userId;
    @Schema(description = "图文内容ID，关联到 app_post 表")
    private Long postId;
    @Schema(description = "点赞时间")
    private LocalDateTime createTime;
    @Schema(description = "是否删除(1-删除，0-未删除)")
    private Integer isDeleted;
}
