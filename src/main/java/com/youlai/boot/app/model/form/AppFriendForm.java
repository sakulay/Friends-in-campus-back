package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 用户好友关系表单对象
 *
 * @author yuyu
 * @since 2025-02-28 18:25
 */
@Getter
@Setter
@Builder
@Schema(description = "用户好友关系表单对象")
public class AppFriendForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;

    @Schema(description = "用户ID，关联到 app_user 表")
    @NotNull(message = "用户ID，关联到 app_user 表不能为空")
    private Long userId;

    @Schema(description = "好友ID，关联到 app_user 表")
    @NotNull(message = "好友ID，关联到 app_user 表不能为空")
    private Long friendId;

    @Schema(description = "好友关系状态，0-待验证，1-已通过，2-已删除")
    private Integer status;


}
