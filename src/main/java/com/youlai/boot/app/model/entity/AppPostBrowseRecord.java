package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 帖子浏览记录实体对象
 *
 * @author yuyu
 * @since 2025-05-03 12:51
 */
@Getter
@Setter
@TableName("app_post_browse_record")
public class AppPostBrowseRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 浏览者用户ID
     */
    private Integer viewerId;
    /**
     * 被浏览的帖子ID
     */
    private Long postId;
    /**
     * 浏览时间
     */
    private LocalDateTime browseTime;
    /**
     * 是否删除（1-删除，0-未删除）
     */
    private Integer isDeleted;
}
