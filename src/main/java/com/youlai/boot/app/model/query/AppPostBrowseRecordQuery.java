package com.youlai.boot.app.model.query;

import com.youlai.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


/**
 * 帖子浏览记录分页查询对象
 *
 * @author yuyu
 * @since 2025-05-03 12:51
 */
@Schema(description ="帖子浏览记录查询对象")
@Getter
@Setter
public class AppPostBrowseRecordQuery extends BasePageQuery {

    @Schema(description = "浏览者用户ID")
    private Integer viewerId;

    @Schema(description = "被浏览的帖子ID")
    private Long postId;

    @Schema(description = "开始浏览时间")
    private LocalDateTime startBrowseTime;

    @Schema(description = "结束浏览时间")
    private LocalDateTime endBrowseTime;
}
