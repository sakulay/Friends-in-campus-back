package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 好友申请视图对象
 *
 * @author yuyu
 * @since 2025-02-28 18:38
 */
@Getter
@Setter
@Schema( description = "好友申请视图对象")
public class AppFriendRequestVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;
    @Schema(description = "申请人ID，关联到 app_user 表")
    private Long senderId;
    @Schema(description = "接收者ID，关联到 app_user 表")
    private Long receiverId;
    @Schema(description = "申请状态，0-待处理，1-已通过，2-已拒绝，3-已撤回")
    private Integer status;
    @Schema(description = "申请时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "是否删除(1-删除，0-未删除)")
    private Integer isDeleted;
}
