package com.xiaohe.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-06 17:23
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("你好");
        System.out.println("你好");
        System.out.println("你好");
        System.out.println("你好");
        System.out.println("你好");
        System.out.println("你好");
        System.out.println("你好");
        System.out.println("你好");
        System.out.println("你好");
    }
}
