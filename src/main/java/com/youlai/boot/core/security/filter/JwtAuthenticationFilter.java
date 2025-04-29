package com.youlai.boot.core.security.filter;

import cn.hutool.core.util.StrUtil;
import com.youlai.boot.common.base.constant.SecurityConstants;
import com.youlai.boot.common.result.ResultCode;
import com.youlai.boot.common.util.ResponseUtils;
import com.youlai.boot.core.security.manager.AppJwtTokenManger;
import com.youlai.boot.core.security.manager.JwtTokenManager;
import com.youlai.boot.core.security.manager.TokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 *  JWT Token 验证和解析过滤器
 *
 * @author Ray.Hao
 * @since 2023/9/13
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenService;
    private final AppJwtTokenManger appJwtTokenService;

    public JwtAuthenticationFilter(JwtTokenManager jwtTokenService, AppJwtTokenManger appJwtTokenService) {
        this.jwtTokenService = jwtTokenService;
        this.appJwtTokenService = appJwtTokenService;
    }


    /**
     * 从请求中获取 JWT Token，校验 JWT Token 是否合法
     * <p>
     * 如果合法则将 Authentication 设置到 Spring Security Context 上下文中
     * 如果不合法则清空 Spring Security Context 上下文，并直接返回响应
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if (StrUtil.isNotBlank(token)) {
                if (token.startsWith(SecurityConstants.JWT_TOKEN_PREFIX)) {
                    token = token.substring(SecurityConstants.JWT_TOKEN_PREFIX.length());
                    // 处理 Web 端 JWT
                    processToken(token, jwtTokenService, response);
                } else if (token.startsWith(SecurityConstants.APP_JWT_TOKEN_PREFIX)) {
                    token = token.substring(SecurityConstants.APP_JWT_TOKEN_PREFIX.length());
                    // 处理 App 端 JWT
                    processToken(token, appJwtTokenService, response);
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            ResponseUtils.writeErrMsg(response, ResultCode.ACCESS_TOKEN_INVALID);
            return;
        }
        // Token有效或无Token时继续执行过滤链
        filterChain.doFilter(request, response);
    }

    /**
     * 通用 Token 处理逻辑
     */
    private void processToken(String token, TokenManager tokenManager, HttpServletResponse response) throws IOException {
        boolean isValid = tokenManager.validateToken(token);
        if (!isValid) {
            ResponseUtils.writeErrMsg(response, ResultCode.ACCESS_TOKEN_INVALID);
            return;
        }
        Authentication authentication = tokenManager.parseToken(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
