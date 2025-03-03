package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 图文图片表单对象
 *
 * @author yuyu
 * @since 2025-03-01 19:27
 */
@Getter
@Setter
@Schema(description = "图文图片表单对象")
public class AppPostImageForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Long id;

    @Schema(description = "图文ID，关联到 app_post 表")
    @NotNull(message = "图文ID，关联到 app_post 表不能为空")
    private Long postId;

    @Schema(description = "图片URL，存储图片路径或链接")
    @NotBlank(message = "图片URL，存储图片路径或链接不能为空")
    @Size(max=255, message="图片URL，存储图片路径或链接长度不能超过255个字符")
    private String imageUrl;

    @Schema(description = "图片删除URL")
    @NotBlank(message = "图片删除URL不能为空")
    @Size(max=255, message="图片删除URL长度不能超过255个字符")
    private String deleteUrl;

    @Schema(description = "图片的显示顺序，值越小排前")
    private Integer imageOrder = 0;

    @Schema(description = "图片上传时间")
    private LocalDateTime createTime;


}
