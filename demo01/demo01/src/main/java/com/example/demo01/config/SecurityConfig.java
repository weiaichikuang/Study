package com.example.demo01.config;

import com.example.demo01.filter.JwtAuthenticationTokenFilter;
import com.example.demo01.handler.LoginFailureHandler;
import com.example.demo01.handler.SGSuccessHandler;
import com.example.demo01.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Title: SecurityConfig
 * @Author 陈友强
 * @Package com.example.demo01.config
 * @Date 2023/11/2 9:06
 * @description:  security配置文件
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Resource
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Resource
    LoginFailureHandler loginFailureHandler;
    @Resource
    SGSuccessHandler sgSuccessHandler;

    //注入
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 从数据库读取的用户进行身份认证



        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {



        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问  已经登录后不可访问
                .antMatchers("/user/login").anonymous()
//                .antMatchers("").permitAll()   无论是否认证对资源放行
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        //捕获security异常统一返回
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

