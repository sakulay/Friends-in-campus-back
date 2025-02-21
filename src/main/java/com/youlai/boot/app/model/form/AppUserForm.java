package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 用户表单对象
 *
 * @author yuyu
 * @since 2025-02-20 22:03
 */
@Getter
@Setter
@Schema(description = "用户表单对象")
public class AppUserForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户唯一ID")
    @NotNull(message = "用户唯一ID不能为空")
    private Integer userId;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Size(max=50, message="用户名长度不能超过50个字符")
    private String username;

    @Schema(description = "用户邮箱")
    @NotBlank(message = "用户邮箱不能为空")
    @Size(max=100, message="用户邮箱长度不能超过100个字符")
    private String email;

    @Schema(description = "用户密码（加密存储）")
    @NotBlank(message = "用户密码（加密存储）不能为空")
    @Size(max=255, message="用户密码（加密存储）长度不能超过255个字符")
    private String password;

    @Schema(description = "用户头像URL")
    @Size(max=255, message="用户头像URL长度不能超过255个字符")
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
