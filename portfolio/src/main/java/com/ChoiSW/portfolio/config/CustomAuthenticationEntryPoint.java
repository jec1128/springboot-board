package com.ChoiSW.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 권한이 없는 상태 ( 즉 로그인이 안된 유저에 한해서) 작동함
@Configuration
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private String DENIED_URL = "/access-denied";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("CustomAuthenticationEntryPoint class, " + "허용되지 않은 접근입니다");
        //throw new com.ChoiSW.portfolio.error.exception.AccessDeniedException("access denied excpetion");
        response.sendRedirect(DENIED_URL);
    }
}
