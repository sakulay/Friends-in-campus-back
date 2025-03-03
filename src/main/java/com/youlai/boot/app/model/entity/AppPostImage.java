package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 图文图片实体对象
 *
 * @author yuyu
 * @since 2025-03-01 19:27
 */
@Getter
@Setter
@TableName("app_post_image")
public class AppPostImage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 图文ID，关联到 app_post 表
     */
    private Long postId;
    /**
     * 图片URL，存储图片路径或链接
     */
    private String imageUrl;
    /**
     * 图片删除URL
     */
    private String deleteUrl;
    /**
     * 图片的显示顺序，值越小排前
     */
    private Integer imageOrder;
    /**
     * 是否删除(1-删除，0-未删除)
     */
    private Integer isDeleted;
}
