package com.youlai.boot.app.model.dto;

import lombok.Data;

import java.util.Collections;
import java.util.HashSet;
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
    /**
     * 数据权限范围，用于控制用户可以访问的数据级别
     *
     * @see com.youlai.boot.common.enums.DataScopeEnum
     */
    private Integer dataScope = 0;
    /**
     * 学生用户角色，默认为"STUDENT"
     */
    private Set<String> roles = new HashSet<>(Collections.singleton("STUDENT"));;

}
