package com.example.demo01.security;

import com.alibaba.fastjson.JSON;
import com.example.demo01.common.ResponseResult;
import com.example.demo01.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult result = new ResponseResult(403, accessDeniedException.getMessage());
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response, json);
    }
}
