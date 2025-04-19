package com.youlai.boot.app.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Schema( description = "用户个人信息视图对象")
public class UnreadVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "发送者id")
    String sender;
    @Schema(description = "发送者头像")
    String avatar;
    @Schema(description = "发送者昵称")
    String nickname;
    @Schema(description = "最新一条聊天记录")
    String latestContent;
    @Schema(description = "未读消息数量")
    String unreadCount;

}