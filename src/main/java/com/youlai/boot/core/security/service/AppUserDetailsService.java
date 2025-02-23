package com.youlai.boot.core.security.service;

import com.youlai.boot.app.model.dto.AppUserAuthInfo;
import com.youlai.boot.app.service.AppUserService;
import com.youlai.boot.core.security.model.AppUserDetails;
import com.youlai.boot.core.security.model.SysUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * App用户认证 DetailsService
 *
 * @author Ray.Hao
 * @since 2021/10/19
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserService userService;

    /**
     * 根据学生号获取用户信息
     *
     * @param studentId 学生号
     * @return 用户信息
     * @throws UsernameNotFoundException 用户名未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        try {
            AppUserAuthInfo userAuthInfo = userService.getUserAuthInfo(studentId);
            //用户信息不存在，或者账号未认证
            if (userAuthInfo == null ) {
                log.info("用户信息不存在，学号: {}", studentId);
                throw new UsernameNotFoundException(studentId);
            }

            if (userAuthInfo.getAuthStatus() == 0) {
                log.error("用户未认证，学号: {}", studentId);
                throw new UsernameNotFoundException(studentId);
            }
            return new AppUserDetails(userAuthInfo);
        } catch (Exception e) {
            // 记录异常日志
            log.error("认证异常:{}", e.getMessage());
            // 抛出异常
            throw e;
        }
    }
}
