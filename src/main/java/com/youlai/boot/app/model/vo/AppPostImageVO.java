package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 图文图片视图对象
 *
 * @author yuyu
 * @since 2025-03-01 19:27
 */
@Getter
@Setter
@Schema( description = "图文图片视图对象")
public class AppPostImageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Long id;
    @Schema(description = "图文ID，关联到 app_post 表")
    private Long postId;
    @Schema(description = "图片URL，存储图片路径或链接")
    private String imageUrl;
    @Schema(description = "图片删除URL")
    private String deleteUrl;
    @Schema(description = "图片的显示顺序，值越小排前")
    private Integer imageOrder;
    @Schema(description = "图片上传时间")
    private LocalDateTime createTime;
    @Schema(description = "是否删除(1-删除，0-未删除)")
    private Integer isDeleted;
}
