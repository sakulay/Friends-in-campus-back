package com.youlai.boot.app.model.dto;

import lombok.Data;

import java.util.Set;

/**
 * 用户认证信息
 *
 * @author yuyu
 * @since 2022/10/22
 */
@Data
public class AppUserAuthInfo {


    /**
     * 学号
     */
    private Long studentId;

    /**
     * 用户密码
     */
    private String password;
    /**
     * 认证状态 0-未认证、1-已认证
     */
    private int authStatus;

    /**
     * 认证身份图片
     */
    private String authInfo;

}
