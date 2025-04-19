package com.youlai.boot.shared.websocket.controller;

import com.youlai.boot.app.model.form.AppChatMessageForm;
import com.youlai.boot.app.service.AppChatMessageService;
import com.youlai.boot.shared.websocket.model.ChatMessage;
import com.youlai.boot.shared.websocket.service.OnlineUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * WebSocket 测试用例控制层
 * <p>
 * 包含点对点/广播发送消息
 *
 * @author Ray
 * @since 2.3.0
 */
@RestController
@RequestMapping("/websocket")
@RequiredArgsConstructor
@Slf4j
public class WebsocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final OnlineUserService onlineUserService;
    private final AppChatMessageService appChatMessageService;

    /**
     * 广播发送消息
     *
     * @param message 消息内容
     */
    @MessageMapping("/sendToAll")
    @SendTo("/topic/notice")
    public String sendToAll(String message) {
        return "服务端通知: " + message;
    }

    /**
     * 点对点发送消息
     * <p>
     * 模拟 张三 给 李四 发送消息场景
     *
     * @param principal 当前用户
     * @param username  接收消息的用户
     * @param message   消息内容
     */
    @Transactional
    @MessageMapping("/sendToUser/{username}")
    public void sendToUser(Principal principal, @DestinationVariable String username, String message) {
        // 发送人
        String sender = principal.getName();
        // 接收人
        String receiver = username;
        // 收集在线接收人集合
        Set<String> receivers = new HashSet<>();
        receivers.add(receiver);
        Set<String> onlineReceivers = onlineUserService.getOnlineReceivers(receivers);
        /** 判断接收人，是否在线
         * 将每个聊天消息、未读消息保存到数据库，并推送消息
         */
        if (onlineReceivers.isEmpty()) {
            log.info("接收人:{}不在线", receiver);
        }
        onlineReceivers.forEach(onlineReceiver -> {
            log.info("接收人:{} 在线", onlineReceiver);
        });

        log.info("发送人:{}; 接收人:{}", sender, receiver);
        String unread = "";
        if (sender.matches("\\d+") && receiver.matches("\\d+")) {
            // 新增聊天消息
            AppChatMessageForm formData = new AppChatMessageForm();
            formData.setSenderId(Long.valueOf(sender));
            formData.setReceiverId(Long.valueOf(receiver));
            formData.setMessageContent(message);
            formData.setMessageType(1);
            appChatMessageService.saveAppChatMessage(formData);
            // 更新未读消息
            unread = appChatMessageService.findBySenderAndReceiverAndType(formData.getSenderId(), formData.getReceiverId());
        }
        // 发送消息给指定用户，拼接后路径 /user/{receiver}/queue/greeting
        messagingTemplate.convertAndSendToUser(receiver, "/queue/greeting", new ChatMessage(sender, message));
        // 发送未读消息数量给指定用户，拼接后路径 /user/{receiver}/queue/unread
        messagingTemplate.convertAndSendToUser(receiver, "/queue/unread", new ChatMessage(
                "UNREAD",
                sender,
                unread + '@' + message)
        );
    }

}
