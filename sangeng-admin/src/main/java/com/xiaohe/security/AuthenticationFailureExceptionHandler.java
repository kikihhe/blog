package com.xiaohe.security;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaohe.constants.Constants;
import com.xiaohe.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 认证失败处理类。
 */
@Component
@Slf4j
public class AuthenticationFailureExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType(Constants.CONTENT_TYPE.APPLICATION_JSON);
        log.error("{}", e);
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(Result.error("认证失败，请重新登录!")));
    }
}
