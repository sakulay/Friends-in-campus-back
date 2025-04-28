package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 校园新闻资讯实体对象
 *
 * @author yuyu
 * @since 2025-04-20 08:10
 */
@Getter
@Setter
@TableName("app_news")
public class AppNews extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String title;
    private String date;
    private String content;
    private Integer isDeleted = 0;
}
