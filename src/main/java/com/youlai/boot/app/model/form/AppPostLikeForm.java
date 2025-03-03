package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 用户点赞记录表单对象
 *
 * @author yuyu
 * @since 2025-03-01 20:51
 */
@Getter
@Setter
@Schema(description = "用户点赞记录表单对象")
public class AppPostLikeForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;

    @Schema(description = "点赞用户ID，关联到 app_user 表")
    @NotNull(message = "点赞用户ID，关联到 app_user 表不能为空")
    private Long userId;

    @Schema(description = "图文内容ID，关联到 app_post 表")
    @NotNull(message = "图文内容ID，关联到 app_post 表不能为空")
    private Long postId;


}
