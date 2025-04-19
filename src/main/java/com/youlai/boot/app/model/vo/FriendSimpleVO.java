package com.youlai.boot.app.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Schema( description = "用户个人信息视图对象")
public class FriendSimpleVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "学号，关联到 app_user 表的学号")
    private Long studentId;
    @Schema(description = "用户昵称")
    private String nickname;
    @Schema(description = "用户头像URL")
    private String avatar;
    @Schema(description = "性别，0-男，1-女")
    private Integer gender;
    @Schema(description = "个人简介")
    private String bio;
    @Schema(description = "好友关系状态，0-未添加，1-已添加，2-已删除")
    private Integer status;
    @Schema(description = "好友请求id")
    private String requestId;
}
