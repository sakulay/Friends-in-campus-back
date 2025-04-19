package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 用户聊天记录表单对象
 *
 * @author yuyu
 * @since 2025-02-28 18:44
 */
@Getter
@Setter
@Schema(description = "用户聊天记录表单对象")
public class AppChatMessageForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;

    @Schema(description = "发送者ID，关联到 app_user 表")
    @NotNull(message = "发送者ID，关联到 app_user 表不能为空")
    private Long senderId;

    @Schema(description = "接收者ID，关联到 app_user 表")
    @NotNull(message = "接收者ID，关联到 app_user 表不能为空")
    private Long receiverId;

    @Schema(description = "消息内容")
    @NotBlank(message = "消息内容不能为空")
    @Size(max=65535, message="消息内容长度不能超过65535个字符")
    private String messageContent;

    @Schema(description = "消息类型，1-文本消息，2-图片，3-语音等")
    private Integer messageType;

    @Schema(description = "是否已读，0-未读，1-已读")
    private Integer isRead;


}
