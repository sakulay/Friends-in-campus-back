package com.youlai.boot.app.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 用户个人信息表单对象
 *
 * @author yuyu
 * @since 2025-02-23 19:43
 */
@Getter
@Setter
@Schema(description = "用户个人信息表单对象")
public class AppUserProfileForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Long id;

    @Schema(description = "学号，关联到 app_user 表的学号")
//    @NotNull(message = "学号，关联到 app_user 表的学号不能为空")
    private Long studentId;

    @Schema(description = "用户昵称")
    @Size(max=255, message="用户昵称长度不能超过255个字符")
    private String nickname;

    @Schema(description = "用户头像URL")
    @Size(max=255, message="用户头像URL长度不能超过255个字符")
    private String avatar;

    @Schema(description = "性别，0-男，1-女")
    private Integer gender;

    @Schema(description = "个人简介")
    @Size(max=65535, message="个人简介长度不能超过65535个字符")
    private String bio;

    @Schema(description = "是否删除（1-删除，0-未删除）")
    private Integer isDeleted;

    @Schema(description = "头像删除地址")
    @Size(max=255, message="头像删除地址长度不能超过255个字符")
    private String deleteUrl;
}
