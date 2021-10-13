package com.ChoiSW.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMsg = exception.getMessage();
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");

        System.out.println("userName : "+userName + ", userPassWord : " + userPassword);
        System.out.println("로그인 실패!!! error : " + errorMsg);

        String DEFAULT_FAILURE_URL = "/account/login?error=true&exception=";
        setDefaultFailureUrl(DEFAULT_FAILURE_URL +errorMsg);
        super.onAuthenticationFailure(request,response,exception);

    }
}