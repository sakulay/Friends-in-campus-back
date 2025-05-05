package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 帖子收藏记录实体对象
 *
 * @author yuyu
 * @since 2025-05-04 12:22
 */
@Getter
@Setter
@TableName("app_post_favorites")
public class AppPostFavorites extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 收藏的用户ID
     */
    private Integer userId;
    /**
     * 被收藏的帖子ID
     */
    private Long postId;
    /**
     * 收藏时间
     */
    private LocalDateTime favoriteTime;
    /**
     * 是否删除（1-删除，0-未删除）
     */
    private Integer isDeleted;
}
