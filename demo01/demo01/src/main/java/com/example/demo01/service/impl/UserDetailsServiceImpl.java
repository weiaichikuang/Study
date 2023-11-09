package com.example.demo01.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo01.entity.LoginUser;
import com.example.demo01.entity.User;
import com.example.demo01.mapper.MenuMapper;
import com.example.demo01.mapper.UserMapper;
import com.example.demo01.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Title: UserDetailsServiceImpl
 * @Author 陈友强
 * @Package com.example.demo01.service.impl
 * @Date 2023/11/2 8:56
 * @description:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //判断用户表中是否有该用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(wrapper);
        return createLoginUser(user);
    }

    public  LoginUser createLoginUser(User user){
        //在这里创建返回需要的loginUser并且查询用户的所有权限
        return new LoginUser(user,permissionService.getRolePermision(user));
    }
}










