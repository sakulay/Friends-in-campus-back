package com.youlai.boot.app.model.query;

import com.youlai.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户个人信息分页查询对象
 *
 * @author yuyu
 * @since 2025-02-23 19:43
 */
@Schema(description ="用户个人信息查询对象")
@Getter
@Setter
public class AppUserProfileQuery extends BasePageQuery {

}
