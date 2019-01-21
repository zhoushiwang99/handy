package com.finals.handy.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

/**
 * @author zsw
 */
public class JwtUtil {

    /**
     * 设置过期时间为两分钟
     */
    private static final long ACCESS_EXPIRE_TIME = 60 * 1000 * 2;

    /**
     * AccessToken密钥
     */
    private static final String ACCESS_SECRET = "access token secret ";

    /**
     * RefreshToken密钥
     */
    private static final String REFRESH_SECRET = "refresh token secret";

    private static final long REFRESH_EXPIRE_TIME = 60 * 1000 * 5;

    /**
     * 生成AccessToken,并放入用户id
     * @param userId 用户的id
     * @return
     */
    public static String createAccessToken(int userId) {
      return createToken(userId,ACCESS_SECRET,ACCESS_EXPIRE_TIME);
    }


    public static String createRefreshToken(int userId) {
       return createToken(userId,REFRESH_SECRET,REFRESH_EXPIRE_TIME);
    }

    /**
     * 生成token
     * @param userId 用户id
     * @param secret token密钥
     * @param expireTime 过期时间
     * @return
     */
    private static String createToken(int userId,String secret,long expireTime){
        Date date = new Date(System.currentTimeMillis() + expireTime);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //在refreshtoken中放入用户的id
        return JWT.create()
                .withClaim("userId",userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }


    /**
     * 验证accessToken是否有效
     * @param accessToken
     * @return
     */
    public static Map<String,Claim> verifyAccessToken(String accessToken) {
        return getStringClaimMap(accessToken, ACCESS_SECRET);
    }

    /**
     * 验证refreshToken 是否有效
     * @param refreshToken
     * @return
     */
    public static Map<String,Claim> verifyRefreshToken(String refreshToken) {
        return getStringClaimMap(refreshToken, REFRESH_SECRET);
    }

    private static Map<String, Claim> getStringClaimMap(String refreshToken, String refreshSecret) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(refreshSecret)).build();
        DecodedJWT jwt = verifier.verify(refreshToken);
        Map<String,Claim> claims = jwt.getClaims();
        return claims;
    }

}
