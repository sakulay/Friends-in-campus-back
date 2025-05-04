package com.youlai.boot.app.model.query;

import com.youlai.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 图文内容分页查询对象
 *
 * @author yuyu
 * @since 2025-03-01 17:11
 */
@Schema(description ="图文内容查询对象")
@Getter
@Setter
public class AppPostQuery extends BasePageQuery {
    @Schema(description = "标题")
    Long userId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "帖子ID列表")
    private List<Long> postIds;

    @Schema(description = "是否按照postIds顺序排序")
    private Boolean orderByPostIds = false;
}
