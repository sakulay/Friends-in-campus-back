package com.youlai.boot.app.model.query;

import com.youlai.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 帖子分类分页查询对象
 *
 * @author yuyu
 * @since 2025-05-14 13:00
 */
@Schema(description ="帖子分类查询对象")
@Getter
@Setter
public class AppPostCategoryQuery extends BasePageQuery {
    @Schema(description = "是否删除")
    private Integer isDeleted = null;
}
