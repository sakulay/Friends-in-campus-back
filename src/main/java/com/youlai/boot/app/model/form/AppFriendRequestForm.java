package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 好友申请表单对象
 *
 * @author yuyu
 * @since 2025-02-28 18:38
 */
@Getter
@Setter
@Schema(description = "好友申请表单对象")
public class AppFriendRequestForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
//    @NotNull(message = "主键，自增不能为空")
    private Integer id;

    @Schema(description = "申请人ID，关联到 app_user 表")
    @NotNull(message = "申请人ID，关联到 app_user 表不能为空")
    private Long senderId;

    @Schema(description = "接收者ID，关联到 app_user 表")
    @NotNull(message = "接收者ID，关联到 app_user 表不能为空")
    private Long receiverId;

    @Schema(description = "申请状态，0-待处理，1-已通过，2-已拒绝，3-已撤回")
    private Integer status = 0;


}
