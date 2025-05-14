package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 帖子分类实体对象
 *
 * @author yuyu
 * @since 2025-05-14 13:00
 */
@Getter
@Setter
@TableName("app_post_category")
public class AppPostCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类描述
     */
    private String description;
    /**
     * 排序字段，值越小越靠前
     */
    private Integer sort;
    /**
     * 是否删除(1-删除，0-未删除)
     */
    private Integer isDeleted;
}
