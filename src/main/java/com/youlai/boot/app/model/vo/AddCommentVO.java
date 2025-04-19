package com.youlai.boot.app.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Schema( description = "用户聊天记录视图对象")
public class AddCommentVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;

    @Schema(description = "发送者信息")
    private FriendSimpleVO userInfo;
}