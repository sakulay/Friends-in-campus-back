package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 用户视图对象
 *
 * @author yuyu
 * @since 2025-02-20 22:03
 */
@Getter
@Setter
@Schema( description = "用户视图对象")
public class AppUserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户唯一ID")
    private Integer userId;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "用户邮箱")
    private String email;
    @Schema(description = "用户密码（加密存储）")
    private String password;
    @Schema(description = "用户头像URL")
    private String avatar;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "创建人ID")
    private Long createBy;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "修改人ID")
    private Long updateBy;
    @Schema(description = "是否删除（1-删除，0-未删除）")
    private Integer isDeleted;
}
