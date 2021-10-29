
package com.ChoiSW.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 권한이 있는 상태 ( 즉 로그인이 된 유저에 한해서)만 작동함
@Configuration
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private String DENIED_URL = "/access-denied";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        System.out.println("CustomAccessDeniedHandler class, " + "허용되지 않은 접근입니다");
        //throw new com.ChoiSW.portfolio.error.exception.AccessDeniedException("access denied excpetion");
        response.sendRedirect(DENIED_URL);
    }
}

