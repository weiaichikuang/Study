package com.example.demo01.utils;


import com.example.demo01.entity.LoginUser;
import com.mysql.cj.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;

/**
 * JWT工具类
 */
@Component
public class JwtUtil {

    @Autowired
    private RedisCache redisCache;

    //有效期为
    public static final Long JWT_TTL = 60 * 60 * 1000L;// 60 * 60 *1000  一个小时
    //设置秘钥明文
    public static final String JWT_KEY = "cikmcikmcikmcikmcikmcikmcikmcikmcikmcikmcikm";

    public static String getUUID() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * 生成jtw
     *
     * @param claims token中要存放的数据（json格式）  是一个键值对  login_user_key：uuid
     * @return
     */
    public static String createJWT(Map<String, Object> claims) {
//        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());// 设置过期时间

        String token = Jwts.builder()
                .setClaims(claims)  //将信息封装进token中
                .signWith(SignatureAlgorithm.HS256, generalKey()).compact();
        return token;
    }

    /**
     * 生成token
     *
     * @param loginUser
     * @return
     */
    public  String createToken(LoginUser loginUser) {
        String userKey = UUID.randomUUID().toString();
        loginUser.setToken(userKey);
//        将loginUser以token中的uuid存入redis中去
        redisCache.setCacheObject(userKey,loginUser);
//        将uuid传入到下一级根据uuid去生成一个token
        Map<String, Object> claims = new HashMap<>();
        claims.put("login_user_key", userKey);
        return JwtUtil.createJWT(claims);
    }


    /**
     * 解析前端传入的token获取用户信息
     */
    public LoginUser getLoginUser(String token ){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_KEY)
                .parseClaimsJws(token)
                .getBody();

        // 解析对应的权限以及用户信息
        //获取到了claims就是封装token时的map
        String uuid = (String) claims.get("login_user_key");
        LoginUser user = redisCache.getCacheObject(uuid);
        System.out.println("log jwtUtil:"+ user);
        return user;

    }





    /**
     * 生成jtw
     *
     * @param subject   token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)              //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer("sg")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    /**
     * jwt
     * 的学习试试
     */
    public static String getJwtStudy() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jws = Jwts.builder().setSubject("NB").signWith(key).compact();
        System.out.println("jwtutils-getjwtstudy" + jws);
        return getJwtStudy();
    }

    /**
     * 创建token
     *
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjYWM2ZDVhZi1mNjVlLTQ0MDAtYjcxMi0zYWEwOGIyOTIwYjQiLCJzdWIiOiJzZyIsImlzcyI6InNnIiwiaWF0IjoxNjM4MTA2NzEyLCJleHAiOjE2MzgxMTAzMTJ9.JVsSbkP94wuczb4QryQbAke3ysBDIL5ou8fWsbt_ebg";
        Claims claims = parseJWT(token);
        System.out.println(claims);
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
        return key;
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
