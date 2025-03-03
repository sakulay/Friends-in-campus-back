package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.*;

/**
 * 图文内容表单对象
 *
 * @author yuyu
 * @since 2025-03-01 17:11
 */
@Getter
@Setter
@Schema(description = "图文内容表单对象")
public class AppPostForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Long id;

    @Schema(description = "发布图文的用户ID，关联到 app_user 表")
    @NotNull(message = "发布图文的用户ID，关联到 app_user 表不能为空")
    private Long userId;

    @Schema(description = "图文标题")
    @Size(max=255, message="图文标题长度不能超过255个字符")
    private String title;

    @Schema(description = "图文内容，支持富文本")
    @Size(max=65535, message="图文内容，支持富文本长度不能超过65535个字符")
    private String content;

    @Schema(description = "点赞数")
    private Integer likeCount = 0;

    @Schema(description = "评论数")
    private Integer commentCount = 0;

    @Schema(description = "文章状态（0-待审核，1-审核通过，2-禁用）")
    private Integer status = 0;

    @Schema(description = "图片列表")
    private List<AppPostImageForm> imageList;

}
