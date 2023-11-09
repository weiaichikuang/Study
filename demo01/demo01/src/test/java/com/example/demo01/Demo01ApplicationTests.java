package com.example.demo01;

import com.example.demo01.entity.User;
import com.example.demo01.mapper.MenuMapper;
import com.example.demo01.mapper.UserMapper;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
class Demo01ApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

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

    @Test
    public void testList(){
        System.out.println(UUID.randomUUID().toString());
    }


    @Test
    public void ThreadLocalTest(){
        ThreadLocal<String> local = new ThreadLocal<>();
//
//        IntStream.range(0, 10).forEach(i -> new Thread(() -> {
//            local.set(Thread.currentThread().getName() + ":" + i);
//            System.out.println("线程：" + Thread.currentThread().getName() + ",local:" + local.get());
//        }).start());


        for (int i = 0; i < 10; i++) {


            local.set(Thread.currentThread().getName()+":"+i);
            System.out.println(local.get()+"-------------------"+local.get());
        }

    }

}
