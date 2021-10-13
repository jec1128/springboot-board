
package com.ChoiSW.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private String DENIED_URL = "/access-denied";

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        System.out.println("CustomAccessDeniedHandler class, " + "허용되지 않은 접근입니다");
        httpServletResponse.sendRedirect(DENIED_URL);
    }
}

