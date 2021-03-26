package com.ChoiSW.portfolio.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final String DEFAULT_FAILURE_URL = "/account/login?error=true&exception=";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMsg = exception.getMessage();
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");

        System.out.println(userName + "  " + userPassword);
        System.out.println("로그인 실패!!! error : " + errorMsg);

        setDefaultFailureUrl(DEFAULT_FAILURE_URL+errorMsg);
        super.onAuthenticationFailure(request,response,exception);

        /*request.setAttribute("username", userName);
        request.setAttribute("userpassword", userPassword);
        request.setAttribute("errormsg", errorMsg);

        response.sendRedirect(DEFAULT_FAILURE_URL+errorMsg);*/
        //request.getRequestDispatcher(DEFAULT_FAILURE_URL+errorMsg).forward(request,response);
    }
}