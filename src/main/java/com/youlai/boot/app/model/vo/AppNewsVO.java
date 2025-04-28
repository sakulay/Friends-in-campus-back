package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 校园新闻资讯视图对象
 *
 * @author yuyu
 * @since 2025-04-20 08:10
 */
@Getter
@Setter
@Schema( description = "校园新闻资讯视图对象")
public class AppNewsVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String date;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;
}
