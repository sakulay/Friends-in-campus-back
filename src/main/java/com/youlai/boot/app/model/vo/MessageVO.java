package com.youlai.boot.app.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Schema( description = "用户个人信息视图对象")
public class MessageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "发送者id")
    String sender;
    @Schema(description = "接收者")
    String receiiver;
    @Schema(description = "内容")
    String content;

}