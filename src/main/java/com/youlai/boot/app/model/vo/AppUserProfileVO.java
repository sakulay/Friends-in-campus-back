package com.youlai.boot.app.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 用户个人信息视图对象
 *
 * @author yuyu
 * @since 2025-02-23 19:43
 */
@Getter
@Setter
@Schema( description = "用户个人信息视图对象")
public class AppUserProfileVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Integer id;
    @Schema(description = "学号，关联到 app_user 表的学号")
    private Long studentId;
    @Schema(description = "用户昵称")
    private String nickname;
    @Schema(description = "用户头像URL")
    private String avatar;
    @Schema(description = "性别，0-男，1-女")
    private Integer gender;
    @Schema(description = "个人简介")
    private String bio;
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
    @Schema(description = "头像删除地址")
    private String deleteUrl;
}
