package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 帖子浏览记录表单对象
 *
 * @author yuyu
 * @since 2025-05-03 12:51
 */
@Getter
@Setter
@Schema(description = "帖子浏览记录表单对象")
public class AppPostBrowseRecordForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "浏览记录ID")
    private Long id;

    @Schema(description = "浏览者用户ID")
    private Integer viewerId;

    @Schema(description = "被浏览的帖子ID")
    private Long postId;

    @Schema(description = "浏览时间")
    private LocalDateTime browseTime;


}
