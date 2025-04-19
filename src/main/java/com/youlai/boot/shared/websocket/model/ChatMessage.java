package com.youlai.boot.shared.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * 系统消息体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    /**
     * 消息类型
     */
    private String type = "CHAT";

    /**
     * 发送者
     */
    private String sender;

    /**
     * 消息内容
     */
    private String content;

    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.content = message;
    }
}
