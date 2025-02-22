package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * app_user ，存储用户的基本信息及认证信息视图对象
 *
 * @author yuyu
 * @since 2025-02-22 19:22
 */
@Getter
@Setter
@Schema( description = "app_user ，存储用户的基本信息及认证信息视图对象")
public class AppUserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "学号，唯一标识")
    private Long studentId;
    @Schema(description = "用户密码")
    private String password;
    @Schema(description = "认证状态，0-未认证，1-已认证")
    private Integer authStatus;
    @Schema(description = "认证信息（如认证图片的URL）")
    private String authInfo;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "创建人ID")
    private Long createBy;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "修改人ID")
    private Long updateBy;
}