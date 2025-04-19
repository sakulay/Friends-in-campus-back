package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 用户聊天记录实体对象
 *
 * @author yuyu
 * @since 2025-02-28 18:44
 */
@Getter
@Setter
@TableName("app_chat_message")
public class AppChatMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 发送者ID，关联到 app_user 表
     */
    private Long senderId;
    /**
     * 接收者ID，关联到 app_user 表
     */
    private Long receiverId;
    /**
     * 消息内容
     */
    private String messageContent;
    /**
     * 消息类型，1-文本消息，2-图片，3-语音, 4-未读消息
     */
    private Integer messageType;
    /**
     * 是否已读，0-未读，1-已读
     */
    private Integer isRead;
    /**
     * 是否删除，0-未删除，1-已删除
     */
    private Integer isDeleted;
}
