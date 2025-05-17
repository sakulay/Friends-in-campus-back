package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * app_user ，存储用户的基本信息及认证信息表单对象
 *
 * @author yuyu
 * @since 2025-02-22 14:31
 */
@Getter
@Setter
@Schema(description = "app_user ，存储用户的基本信息及认证信息表单对象")
public class AppUserForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "学号，唯一标识")
//    @NotNull(message = "学号，唯一标识不能为空")
    private Long studentId;

    @Schema(description = "用户密码")
//    @NotBlank(message = "用户密码不能为空")
    @Size(max=255, message="用户密码长度不能超过255个字符")
    private String password;

    @Schema(description = "认证状态，0-未认证，1-已认证")
    private Integer authStatus;

    @Schema(description = "认证信息（如认证图片的URL）")
//    @NotBlank(message = "认证信息（如认证图片的URL）不能为空")
    @Size(max=255, message="认证信息（如认证图片的URL）长度不能超过255个字符")
    private String authInfo;

    @Schema(description = "图片删除api")
    private String deleteUrl;
    
    @Schema(description = "是否删除（1-删除，0-未删除）")
    private Integer isDeleted;
}
