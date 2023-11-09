package com.example.demo01.service.impl;

import com.example.demo01.common.ResponseResult;
import com.example.demo01.entity.LoginUser;
import com.example.demo01.entity.User;
import com.example.demo01.service.LoginServcie;
import com.example.demo01.utils.JwtUtil;
import com.example.demo01.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Title: LoginServiceImpl
 * @Author 陈友强
 * @Package com.example.demo01.service.impl
 * @Date 2023/11/2 9:11
 * @description:
 */
@Service
public class LoginServiceImpl implements LoginServcie {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    private LoginUser loginUser;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseResult login(User user) {

//
//        try {
            //是一个将账号密码封装起来的一个方法
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername   返回一个LoginUser里面包含账号密码和对应的权限
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);


//            //使用userid生成token
//            loginUser = (LoginUser) authenticate.getPrincipal();
//            String userId = loginUser.getUser().getId().toString();
//            String jwt = JwtUtil.createJWT(userId);
//            //authenticate存入redis
//            redisCache.setCacheObject("login:" + userId, loginUser);
//            //把token响应给前端
//            HashMap<String, String> map = new HashMap<>();
//            map.put("token", jwt);

//            获取根据用户名查到的信息包括账号密码与权限
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
//          生成一个token并且存入到将其和loginuser信息存入到redis中去
            String token = jwtUtil.createToken(loginUser);
            //把token响应给前端
            HashMap<String, String> map = new HashMap<>();
            map.put("token", token);
            return new ResponseResult(200, "登陆成功", map);
//        } catch (Exception e) {
////            如果有错
//            return new ResponseResult(200, e.getMessage());
//        }

    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
//        redisCache.deleteObject("login:" + userid);
        return new ResponseResult(200, "退出成功");
    }
}

