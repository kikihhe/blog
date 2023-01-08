package com.xiaohe.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaohe.constants.Constants;
import com.xiaohe.utils.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-06 21:38
 */
public class LogoutHandler implements LogoutSuccessHandler {
    private StringRedisTemplate stringRedisTemplate;

    public LogoutHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String token = httpServletRequest.getHeader("token");

        if (Strings.isBlank(token)) {
            httpServletResponse.setContentType(Constants.CONTENT_TYPE.APPLICATION_JSON);
            httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(Result.success("您还未登录，请勿退出登录!")));
            return;
        }
        Boolean delete = stringRedisTemplate.delete(Constants.User.BLOG_LOGIN_TOKEN + token);

        if (Boolean.TRUE.equals(delete)) {
            httpServletResponse.setContentType(Constants.CONTENT_TYPE.APPLICATION_JSON);
            httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(Result.success("操作成功")));
        } else {
            httpServletResponse.setContentType(Constants.CONTENT_TYPE.APPLICATION_JSON);
            httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(Result.success("操作失败，请重试!")));
        }

    }
}
