package com.youlai.boot.app.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 修改密码表单对象
 *
 * @author yuyu
 * @since 2025-03-15
 */
@Getter
@Setter
@Schema(description = "修改密码表单对象")
public class UpdatePasswordForm {

    @Schema(description = "学号")
    @NotNull(message = "学号不能为空")
    private Long studentId;

    @Schema(description = "旧密码")
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @Schema(description = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
} 