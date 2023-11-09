package com.example.demo01.service;

import com.example.demo01.entity.User;
import com.example.demo01.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户权限的处理
 */
@Component
public class PermissionService {

    @Autowired
    private MenuMapper menuMapper;

    public List<String> getRolePermision(User user){


        List<String> perms = new ArrayList<>();

//        管理员所拥有的权限
        if (user.getId() != null && user.getId() == 1){
            perms.add("*:*:*");
        }
//        其他的用户权限
        else {
//          //根据用户id获得该用户所拥有的权限
            List<String> strings = menuMapper.selectPermsByUserId(user.getId());
            perms.addAll(strings);
        }
        return perms;
    }

}
