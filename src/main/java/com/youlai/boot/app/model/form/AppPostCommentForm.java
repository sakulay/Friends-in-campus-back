package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 图文评论表单对象
 *
 * @author yuyu
 * @since 2025-03-01 17:24
 */
@Getter
@Setter
@Schema(description = "图文评论表单对象")
public class AppPostCommentForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;

    @Schema(description = "评论用户ID，关联到 app_user 表")
    @NotNull(message = "评论用户ID，关联到 app_user 表不能为空")
    private Long userId;

    @Schema(description = "图文内容ID，关联到 app_post 表")
    @NotNull(message = "图文内容ID，关联到 app_post 表不能为空")
    private Long postId;

    @Schema(description = "评论内容")
    @NotBlank(message = "评论内容不能为空")
    @Size(max=65535, message="评论内容长度不能超过65535个字符")
    private String content;

    @Schema(description = "回复的评论ID，NULL表示主评论")
    private Integer parentId;

    @Schema(description = "是否为回复评论(0为评论，1为回复)")
    private Integer isReply;

    @Schema(description = "审核状态（0 - 待审核，1 - 已通过， 2 - 禁用）")
    private Integer status;


}
