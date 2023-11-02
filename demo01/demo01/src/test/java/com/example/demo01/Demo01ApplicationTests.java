package com.example.demo01;

import com.example.demo01.entity.User;
import com.example.demo01.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class Demo01ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;
    @Test
    void contextLoads() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUserName("sg4");
        user.setNickName("sg4");
        String encode = bCryptPasswordEncoder.encode("123456");
        user.setPassword(encode);
        user.setStatus("0");
        user.setSex("0");
        user.setUserType("1");
        user.setCreateBy(1L);
        userMapper.insert(user);
    }


    @Test
    public void insertUserTest(){

        User user = new User();
        user.setUserName("qiang");
        user.setNickName("qiang");
        user.setPassword("123456");
        user.setStatus("0");
        user.setUserType("0");
        user.setCreateBy(1L);
        int users = userMapper.insert(user);
        System.out.println("------------------"+users);
    }
}
