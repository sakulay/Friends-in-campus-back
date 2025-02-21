package com.youlai.boot.app.model.query;

import com.youlai.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户分页查询对象
 *
 * @author yuyu
 * @since 2025-02-20 22:03
 */
@Schema(description ="用户查询对象")
@Getter
@Setter
public class AppUserQuery extends BasePageQuery {

}
