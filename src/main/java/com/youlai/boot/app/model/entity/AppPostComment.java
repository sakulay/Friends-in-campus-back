package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 图文评论实体对象
 *
 * @author yuyu
 * @since 2025-03-01 17:24
 */
@Getter
@Setter
@TableName("app_post_comment")
public class AppPostComment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 评论用户ID，关联到 app_user 表
     */
    private Long userId;
    /**
     * 图文内容ID，关联到 app_post 表
     */
    private Long postId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 回复的评论ID，NULL表示主评论
     */
    private Integer parentId;
    /**
     * 是否删除(1-删除，0-未删除)
     */
    private Integer isDeleted;
    /**
     * 是否为回复评论(0为评论，1为回复)
     */
    private Integer isReply;
    /**
     * 审核状态（0 - 待审核，1 - 已通过， 2 - 禁用）
     */
    private Integer status;
}
