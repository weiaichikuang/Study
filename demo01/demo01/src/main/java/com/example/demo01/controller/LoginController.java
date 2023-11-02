package com.example.demo01.controller;

import com.example.demo01.common.ResponseResult;
import com.example.demo01.entity.User;
import com.example.demo01.service.LoginServcie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Title: LoginController
 * @Author 陈友强
 * @Package com.example.demo01.controller
 * @Date 2023/11/2 9:09
 * @description:
 */
@RestController
public class LoginController {

    @Resource
    private LoginServcie loginServcie;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){

        return loginServcie.login(user);
    }
}

