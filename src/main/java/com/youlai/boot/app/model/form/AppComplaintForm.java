package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 用户举报记录表单对象
 *
 * @author yuyu
 * @since 2025-04-22 20:49
 */
@Getter
@Setter
@Schema(description = "用户举报记录表单对象")
public class AppComplaintForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;

    @Schema(description = "举报用户ID，关联到 app_user 表")
    private Long userId;

    @Schema(description = "举报描述，说明举报原因")
    @Size(max=65535, message="举报描述，说明举报原因长度不能超过65535个字符")
    private String complaintDescription;

    @Schema(description = "举报目标类型，1-图文，2-评论")
    private Integer complaintTargetType;

    @Schema(description = "举报目标ID（对应图文ID或评论ID）")
    private Long complaintTargetId;

    @Schema(description = "举报状态，0-待处理，1-已受理，2-已处理，3-已驳回")
    private Integer status;

}
