package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 用户好友关系视图对象
 *
 * @author yuyu
 * @since 2025-02-28 18:25
 */
@Getter
@Setter
@Schema( description = "用户好友关系视图对象")
public class AppFriendVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;
    @Schema(description = "用户ID，关联到 app_user 表")
    private Long userId;
    @Schema(description = "好友ID，关联到 app_user 表")
    private Long friendId;
    @Schema(description = "好友关系状态，0-待验证，1-已通过，2-已删除")
    private Integer status;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "是否删除（1-删除，0-未删除）")
    private Integer isDeleted;
}
