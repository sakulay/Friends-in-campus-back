package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 帖子收藏记录表单对象
 *
 * @author yuyu
 * @since 2025-05-04 12:22
 */
@Getter
@Setter
@Schema(description = "帖子收藏记录表单对象")
public class AppPostFavoritesForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "收藏记录ID")
    private Long id;

    @Schema(description = "收藏的用户ID")
    private Integer userId;

    @Schema(description = "被收藏的帖子ID")
    private Long postId;

    @Schema(description = "收藏时间")
    private LocalDateTime favoriteTime;


}
