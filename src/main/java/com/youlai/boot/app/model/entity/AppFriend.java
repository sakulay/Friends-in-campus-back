package com.youlai.boot.app.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 用户好友关系实体对象
 *
 * @author yuyu
 * @since 2025-02-28 18:25
 */
@Getter
@Setter
@TableName("app_friend")
public class AppFriend extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID，关联到 app_user 表
     */
    private Long userId;
    /**
     * 好友ID，关联到 app_user 表
     */
    private Long friendId;
    /**
     * 好友关系状态，0-待验证，1-已通过，2-已删除
     */
    private Integer status;
    /**
     * 创建人ID
     */
    private Long createBy;
    /**
     * 修改人ID
     */
    private Long updateBy;
    /**
     * 是否删除（1-删除，0-未删除）
     */
    private Integer isDeleted;
}
