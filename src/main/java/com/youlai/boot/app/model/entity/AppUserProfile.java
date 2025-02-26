package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 用户个人信息实体对象
 *
 * @author yuyu
 * @since 2025-02-23 19:43
 */
@Getter
@Setter
@TableName("app_user_profile")
public class AppUserProfile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 学号，关联到 app_user 表的学号
     */
    private Long studentId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像URL
     */
    private String avatar;
    /**
     * 性别，0-男，1-女
     */
    private Integer gender;
    /**
     * 个人简介
     */
    private String bio = "这个人很神秘，什么也没有留下！";
    /**
     * 创建人ID
     */
    private Long createBy;
    /**
     * 修改人ID
     */
    private Long updateBy;
    /**
     * 是否删除（1-删除，0-未删除）
     */
    private Integer isDeleted;
    /**
     * 头像删除地址
     */
    private String deleteUrl;
}
