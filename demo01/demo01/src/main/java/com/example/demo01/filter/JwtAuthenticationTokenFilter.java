package com.example.demo01.filter;

import com.example.demo01.common.ResponseResult;
import com.example.demo01.entity.LoginUser;
import com.example.demo01.utils.JwtUtil;
import com.example.demo01.utils.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Title: JwtAuthenticationTokenFilter
 * @Author 陈友强
 * @Package com.example.demo01.filter
 * @Date 2023/11/2 9:16
 * @description:  这是一个过滤器会获取请求头中的token对token解析出其userid然后使用userid去redis中查询其对应的信息
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        //解析token
        if (!StringUtils.hasText(token)) {  //验证该token不能为null不能为空不能为” “
            //放行
            filterChain.doFilter(request, response);
//            throw new RuntimeException("没有token");
            return;
        }else {
            try {
                LoginUser loginUser = jwtUtil.getLoginUser(token);

                //  用于在应用程序中获取当前用户的认证信息
                SecurityContext context = SecurityContextHolder.getContext();

                System.out.println("log context:"+context.getAuthentication());
                //存入SecurityContextHolder
                //TODO 获取权限信息封装到Authentication中
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }catch (Exception e){
                System.out.println("error" + e.getMessage());
            }
        }



//        //解析token
//        String userid;
//        try {
//            Claims claims = JwtUtil.parseJWT(token);
//            userid = claims.getSubject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("token非法");
//        }
//        //从redis中获取用户信息
//        String redisKey = "login:" + userid;
//        LoginUser loginUser = redisCache.getCacheObject(redisKey);
//
//        System.out.println("log  redis获取的信息"+loginUser.getAuthorities());

//        if(Objects.isNull(loginUser)){
//            throw new RuntimeException("用户未登录");
//        }

        //放行
        filterChain.doFilter(request, response);
    }
}
