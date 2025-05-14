package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 帖子分类表单对象
 *
 * @author yuyu
 * @since 2025-05-14 13:00
 */
@Getter
@Setter
@Schema(description = "帖子分类表单对象")
public class AppPostCategoryForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Long id;

    @Schema(description = "分类名称")
    @Size(max=50, message="分类名称长度不能超过50个字符")
    private String name;

    @Schema(description = "分类描述")
    @Size(max=255, message="分类描述长度不能超过255个字符")
    private String description;

    @Schema(description = "排序字段，值越小越靠前")
    private Integer sort;


}
