package com.youlai.boot.app.model.query;

import com.youlai.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 校园新闻资讯分页查询对象
 *
 * @author yuyu
 * @since 2025-04-20 08:10
 */
@Schema(description ="校园新闻资讯查询对象")
@Getter
@Setter
public class AppNewsQuery extends BasePageQuery {

}
