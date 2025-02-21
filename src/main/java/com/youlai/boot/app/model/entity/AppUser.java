package com.youlai.boot.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.boot.common.base.BaseEntity;

/**
 * 用户实体对象
 *
 * @author yuyu
 * @since 2025-02-20 22:03
 */
@Getter
@Setter
@TableName("app_user")
public class AppUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户密码（加密存储）
     */
    private String password;
    /**
     * 用户头像URL
     */
    private String avatar;
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
}
