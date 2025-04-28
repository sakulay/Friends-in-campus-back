package com.youlai.boot.app.model.query;

import com.youlai.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户举报记录分页查询对象
 *
 * @author yuyu
 * @since 2025-04-22 20:49
 */
@Schema(description ="用户举报记录查询对象")
@Getter
@Setter
public class AppComplaintQuery extends BasePageQuery {

}
