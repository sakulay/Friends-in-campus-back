package com.youlai.boot.core.security.manager;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.youlai.boot.common.constant.JwtClaimConstants;
import com.youlai.boot.common.constant.SecurityConstants;
import com.youlai.boot.common.exception.BusinessException;
import com.youlai.boot.common.result.ResultCode;
import com.youlai.boot.config.property.SecurityProperties;
import com.youlai.boot.core.security.model.AppUserDetails;
import com.youlai.boot.core.security.model.AuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ConditionalOnProperty(value = "security.session.type", havingValue = "jwt")
@Service("appJwtTokenManger")
@Slf4j
public class AppJwtTokenManger implements TokenManager{
    private final SecurityProperties securityProperties;
    private final RedisTemplate<String, Object> redisTemplate;
    private final byte[] secretKey;

    public AppJwtTokenManger(SecurityProperties securityProperties, RedisTemplate<String, Object> redisTemplate){
        this.securityProperties = securityProperties;
        this.redisTemplate = redisTemplate;
        this.secretKey = securityProperties.getJwt().getKey().getBytes();
    }

    /**
     * 生成令牌
     * @param authentication 认证信息
     * @return 令牌响应对象
     * token存入用户ID，用户名
     */
    @Override
    public AuthenticationToken generateToken(Authentication authentication) {
        log.info("appTokenManger正在生成AppToken");
        int accessTokenTimeToLive = securityProperties.getJwt().getAccessTokenTimeToLive();
        int refreshTokenTimeToLive = securityProperties.getJwt().getRefreshTokenTimeToLive();

        String accessToken = generateToken(authentication, accessTokenTimeToLive);
        String refreshToken = generateToken(authentication, refreshTokenTimeToLive);

        return AuthenticationToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("AppBearer")
                .expiresIn(accessTokenTimeToLive)
                .build();
    }
    /**
     * 解析令牌
     * @param token JWT Token
     * @return Authentication 对象
     */
    @Override
    public Authentication parseToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        AppUserDetails userDetails = new AppUserDetails();
        userDetails.setStudentId(payloads.getStr(JwtClaimConstants.STUDENT_ID)); //用户ID
        userDetails.setDataScope(payloads.getInt(JwtClaimConstants.DATA_SCOPE)); // 数据权限范围
        // 角色集合
        Set<SimpleGrantedAuthority> authorities = payloads.getJSONArray(JwtClaimConstants.AUTHORITIES)
                .stream()
                .map(authority -> new SimpleGrantedAuthority(Convert.toStr(authority)))
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    /**
     * 校验令牌
     *
     * @param token JWT Token
     * @return 是否有效
     */
    @Override
    public boolean validateToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        // 检查 Token 是否有效(验签 + 是否过期)
        boolean isValid = jwt.setKey(secretKey).validate(0);

        if (isValid) {
            // 检查 Token 是否已被加入黑名单(注销、修改密码等场景)
            JSONObject payloads = jwt.getPayloads();
            String jti = payloads.getStr(JWTPayload.JWT_ID);

            // 判断是否在黑名单中，如果在，则返回false 标识Token无效
            if (Boolean.TRUE.equals(redisTemplate.hasKey(SecurityConstants.BLACKLIST_TOKEN_PREFIX + jti))) {
                return false;
            }
        }
        return isValid;
    }

    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 令牌响应对象
     */
    @Override
    public AuthenticationToken refreshToken(String refreshToken) {
        boolean isValid = validateToken(refreshToken);
        if (!isValid) {
            throw new BusinessException(ResultCode.REFRESH_TOKEN_INVALID);
        }

        Authentication authentication = parseToken(refreshToken);
        int accessTokenExpiration = securityProperties.getJwt().getRefreshTokenTimeToLive();
        String newAccessToken = generateToken(authentication, accessTokenExpiration);

        return AuthenticationToken.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(accessTokenExpiration)
                .build();
    }

    @Override
    public void blacklistToken(String token) {
        if (token.startsWith(SecurityConstants.APP_JWT_TOKEN_PREFIX)) {
            token = token.substring(SecurityConstants.APP_JWT_TOKEN_PREFIX.length());
        }
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        String jti = payloads.getStr(JWTPayload.JWT_ID);
        Integer expirationAt = payloads.getInt(JWTPayload.EXPIRES_AT);

        if (expirationAt != null) {
            int currentTimeSeconds = Convert.toInt(System.currentTimeMillis() / 1000);
            if (expirationAt < currentTimeSeconds) {
                // Token已过期，直接返回
                return;
            }
            // 计算Token剩余时间，将其加入黑名单
            int expirationIn = expirationAt - currentTimeSeconds;
            redisTemplate.opsForValue().set(SecurityConstants.BLACKLIST_TOKEN_PREFIX + jti, null, expirationIn, TimeUnit.SECONDS);
        } else {
            // 永不过期的Token永久加入黑名单
            redisTemplate.opsForValue().set(SecurityConstants.BLACKLIST_TOKEN_PREFIX + jti, null);
        }
        ;
    }

    /**
     * 生成 JWT Token
     *
     * @param authentication 认证信息
     * @param ttl           过期时间blacklistToken
     * @return
     */
    private String generateToken(Authentication authentication, int ttl) {
        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        Map<String, Object> payload = new HashMap<>();
        payload.put(JwtClaimConstants.STUDENT_ID, appUserDetails.getStudentId()); // 学生ID
        payload.put(JwtClaimConstants.DATA_SCOPE, appUserDetails.getDataScope()); // 数据权限范围

        // claims 中添加角色信息
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        payload.put(JwtClaimConstants.AUTHORITIES, roles);
        Date now = new Date();
        payload.put(JWTPayload.ISSUED_AT, now);

        // 设置过期时间 -1 表示永不过期
        if (ttl != -1) {
            Date expiresAt = DateUtil.offsetSecond(now, ttl);
            payload.put(JWTPayload.EXPIRES_AT, expiresAt);
        }
        payload.put(JWTPayload.SUBJECT, authentication.getName());
        payload.put(JWTPayload.JWT_ID, IdUtil.simpleUUID());

        return JWTUtil.createToken(payload, secretKey);
    }


}
