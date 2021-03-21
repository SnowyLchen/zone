package com.cjl.basci.zone.framework.shiro.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cjl.basci.zone.framework.shiro.StatelessConstants;
import com.cjl.basci.zone.utils.dateutils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author hd_zhu
 */
public class JwtUtil {
    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 生成JWT签名
     *
     * @param loginName 登录账号
     * @return 携带登录信息的字符串 {@link String}
     */
    @NonNull
    public static String sign(@NonNull final String loginName) {
        // 计算Jwt过期时间
        Instant instant = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(StatelessConstants.JWT_EXPIRE));
        Date expireDate = Date.from(instant);
        // 当前时间
        Date nowDate = DateUtils.getNowDate();
        // 配置jwt加密方式
        Algorithm algorithm = Algorithm.HMAC256(StatelessConstants.JWT_SECRET);
        // 使用loginName生成Jwt签名
        return JWT.create()
                .withIssuer(StatelessConstants.JWT_IS_UER)
                .withIssuedAt(nowDate)
                .withClaim(StatelessConstants.JWT_DEFAULT_CLAIM, loginName)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    /**
     * 验证jwt是否有效
     *
     * @param token token
     * @return
     */
    public static boolean verify(@NonNull final String token) {
        // 配置jwt加密方式
        Algorithm algorithm = Algorithm.HMAC256(StatelessConstants.JWT_SECRET);
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(StatelessConstants.JWT_IS_UER)
                    .build();
            DecodedJWT verify = verifier.verify(token);
            if (verify.getClaim(StatelessConstants.JWT_DEFAULT_CLAIM).asString().equals(getLoginNameByToken(token))) {
                return true;
            }
        } catch (JWTDecodeException e) {
            logger.error("jwt验证失败,{}", e.getMessage());
        }
        return false;
    }

    /**
     * 通过token获取登录名
     *
     * @param token 登录名
     * @return
     */
    public static String getLoginNameByToken(@NonNull final String token) {
        try {
            DecodedJWT decode = JWT.decode(token);
            return decode.getClaim(StatelessConstants.JWT_DEFAULT_CLAIM)
                    .asString();
        } catch (JWTDecodeException | NullPointerException e) {
            return null;
        }
    }
}
