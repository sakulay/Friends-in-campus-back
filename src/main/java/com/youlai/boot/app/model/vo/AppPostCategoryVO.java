package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 帖子分类视图对象
 *
 * @author yuyu
 * @since 2025-05-14 13:00
 */
@Getter
@Setter
@Schema( description = "帖子分类视图对象")
public class AppPostCategoryVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Long id;
    @Schema(description = "分类名称")
    private String name;
    @Schema(description = "分类描述")
    private String description;
    @Schema(description = "排序字段，值越小越靠前")
    private Integer sort;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "是否删除")
    private Integer isDeleted;
}
