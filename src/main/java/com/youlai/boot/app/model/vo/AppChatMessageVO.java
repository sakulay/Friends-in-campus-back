package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 用户聊天记录视图对象
 *
 * @author yuyu
 * @since 2025-02-28 18:44
 */
@Getter
@Setter
@Schema( description = "用户聊天记录视图对象")
public class AppChatMessageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;
    @Schema(description = "发送者ID，关联到 app_user 表")
    private Long senderId;
    @Schema(description = "接收者ID，关联到 app_user 表")
    private Long receiverId;
    @Schema(description = "消息内容")
    private String messageContent;
    @Schema(description = "消息类型，1-文本消息，2-图片，3-语音等")
    private Integer messageType;
    @Schema(description = "消息发送时间")
    private LocalDateTime createTime;
    @Schema(description = "是否已读，0-未读，1-已读")
    private Integer isRead;
    @Schema(description = "是否删除，0-未删除，1-已删除")
    private Integer isDeleted;
}
