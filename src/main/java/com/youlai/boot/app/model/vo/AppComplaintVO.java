package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 用户举报记录视图对象
 *
 * @author yuyu
 * @since 2025-04-22 20:49
 */
@Getter
@Setter
@Schema( description = "用户举报记录视图对象")
public class AppComplaintVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;
    @Schema(description = "举报用户ID，关联到 app_user 表")
    private Long userId;
    @Schema(description = "举报类型（例如：恶意内容、色情暴力等）")
    private String complaintType;
    @Schema(description = "举报描述，说明举报原因")
    private String complaintDescription;
    @Schema(description = "举报目标类型，1-图文，2-评论")
    private Integer complaintTargetType;
    @Schema(description = "举报目标ID（对应图文ID或评论ID）")
    private Long complaintTargetId;
    @Schema(description = "举报状态，0-待处理，1-已受理，2-已处理，3-已驳回")
    private Integer status;
    @Schema(description = "举报时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "是否删除(1-删除，0-未删除)")
    private Integer isDeleted;
}
