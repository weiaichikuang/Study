package com.example.demo01.security;

import com.alibaba.fastjson.JSON;
import com.example.demo01.common.ResponseResult;
import com.example.demo01.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult result = new ResponseResult(401, authException.getMessage());
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response, json);
    }
}

