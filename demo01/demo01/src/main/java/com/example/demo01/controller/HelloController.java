package com.example.demo01.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: HelloController
 * @Author 陈友强
 * @Package com.example.demo01.controller
 * @Date 2023/11/2 8:42
 * @description:
 */
@RestController
@RequestMapping("/hello")

public class HelloController {
    @PreAuthorize("hasAuthority('system:book:list')")
    @RequestMapping("/111")
    public String hello(){
        return "hello";
    }

    @PreAuthorize("hasAuthority('system:book:info')")
    @RequestMapping("/222")
    public String hello2(){
        return "hello222";
    }
}
