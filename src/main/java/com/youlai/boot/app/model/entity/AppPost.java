package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 图文内容实体对象
 *
 * @author yuyu
 * @since 2025-03-01 17:11
 */
@Getter
@Setter
@TableName("app_post")
public class AppPost extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 发布图文的用户ID，关联到 app_user 表
     */
    private Long userId;
    /**
     * 图文标题
     */
    private String title;
    /**
     * 图文内容，支持富文本
     */
    private String content;
    /**
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 是否删除(1-删除，0-未删除)
     */
    private Integer isDeleted;
    /**
     * 文章状态（0-待审核，1-审核通过，2-禁用）
     */
    private Integer status;
}
