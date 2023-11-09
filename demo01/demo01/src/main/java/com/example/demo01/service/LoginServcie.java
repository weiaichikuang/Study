package com.example.demo01.service;

import com.example.demo01.common.ResponseResult;
import com.example.demo01.entity.User;

/**
 * @Title: LoginServcie
 * @Author 陈友强
 * @Package com.example.demo01.service.impl
 * @Date 2023/11/2 9:11
 * @description:
 */
public interface LoginServcie {
    ResponseResult login(User user);


    ResponseResult logout();
}
