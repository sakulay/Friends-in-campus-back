package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 用户点赞记录实体对象
 *
 * @author yuyu
 * @since 2025-03-01 20:51
 */
@Getter
@Setter
@TableName("app_post_like")
public class AppPostLike extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 点赞用户ID，关联到 app_user 表
     */
    private Long userId;
    /**
     * 图文内容ID，关联到 app_post 表
     */
    private Long postId;
    /**
     * 是否删除(1-删除，0-未删除)
     */
    private Integer isDeleted;
}
