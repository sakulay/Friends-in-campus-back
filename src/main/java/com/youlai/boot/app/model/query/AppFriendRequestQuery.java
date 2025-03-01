package com.youlai.boot.app.model.query;

import com.youlai.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 好友申请分页查询对象
 *
 * @author yuyu
 * @since 2025-02-28 18:38
 */
@Schema(description ="好友申请查询对象")
@Getter
@Setter
public class AppFriendRequestQuery extends BasePageQuery {

}
