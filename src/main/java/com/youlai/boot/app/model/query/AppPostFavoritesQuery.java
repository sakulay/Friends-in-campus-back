package com.youlai.boot.app.model.query;

import com.youlai.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 帖子收藏记录分页查询对象
 *
 * @author yuyu
 * @since 2025-05-04 12:22
 */
@Schema(description = "帖子收藏记录查询对象")
@Getter
@Setter
public class AppPostFavoritesQuery extends BasePageQuery {

    @Schema(description = "收藏者用户ID")
    private Long viewerId;

    @Schema(description = "被收藏的帖子ID")
    private Long postId;

    @Schema(description = "开始收藏时间")
    private LocalDateTime startFavoriteTime;

    @Schema(description = "结束收藏时间")
    private LocalDateTime endFavoriteTime;

}