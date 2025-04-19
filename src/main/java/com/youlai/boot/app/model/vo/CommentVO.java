package com.youlai.boot.app.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema( description = "用户个人信息视图对象")
public class CommentVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "评论ID")
    private Long id;
    @Schema(description = "用户ID")
    private Long userId;
    @Schema(description = "头像地址")
    private String url;
    @Schema(description = "用户名")
    private String user;
    @Schema(description = "用户信息")
    private FriendSimpleVO userInfo;
    @Schema(description = "回复ID, 同parentId")
    private Long replyId;
    @Schema(description = "回复用户名")
    private String replyUser;
    @Schema(description = "评论内容")
    private String text;
    @Schema(description = "评论时间")
    private LocalDateTime commentTime;
    @Schema(description = "点赞数")
    private Integer likeCount;
    @Schema(description = "回复列表")
    private List<CommentVO> replyList;

}