package com.xiaohe.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaohe.constants.Constants;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-06 16:49
 */

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 判断是不是post请求
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        // 判断数据的格式是不是json格式
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            // 从json中取出数据，封装到UsernamePasswordAuthenticationToken里
            Map map = null;
            String username = null;
            String password = null;
            try {
                map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Objects.isNull(map)) {
                throw new RuntimeException("用户名/密码不能为空");
            }
            username = (String) map.get(Constants.User.LOGIN_PARAMETER_USERNAME);
            password = (String) map.get(Constants.User.LOGIN_PARAMETER_PASSWORD);
            if (Objects.isNull(username) || Objects.isNull(password)) {
                throw new RuntimeException("用户名/密码不能为空");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            setDetails(request, authenticationToken);
            return this.getAuthenticationManager().authenticate(authenticationToken);
        }


        return super.attemptAuthentication(request, response);
    }
}
