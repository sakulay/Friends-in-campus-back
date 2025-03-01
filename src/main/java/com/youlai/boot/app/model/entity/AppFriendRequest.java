package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 好友申请实体对象
 *
 * @author yuyu
 * @since 2025-02-28 18:38
 */
@Getter
@Setter
@TableName("app_friend_request")
public class AppFriendRequest extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 申请人ID，关联到 app_user 表
     */
    private Long senderId;
    /**
     * 接收者ID，关联到 app_user 表
     */
    private Long receiverId;
    /**
     * 申请状态，0-待处理，1-已通过，2-已拒绝，3-已撤回
     */
    private Integer status;
    /**
     * 是否删除(1-删除，0-未删除)
     */
    private Integer isDeleted;
}
