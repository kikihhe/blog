package com.xiaohe.security;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaohe.constants.Constants;
import com.xiaohe.domain.entity.LoginUser;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.BlogUserLoginVo;
import com.xiaohe.domain.vo.UserInfoVo;
import com.xiaohe.utils.JWTUtils;
import com.xiaohe.utils.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private StringRedisTemplate stringRedisTemplate;

    public LoginSuccessHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User user = loginUser.getUser();
        user.setPassword(null);
        // 根据用户信息生成token

        ObjectMapper objectMapper = new ObjectMapper();
        // token为键，json为值 存入redis
        String json = objectMapper.writeValueAsString(user);
        String token = JWTUtils.generateToken(objectMapper.readValue(json, Map.class));
        // 存入redis，以 token-json形式存储, token存活时间为30mins
        stringRedisTemplate.opsForValue().set(Constants.User.BLOG_LOGIN_TOKEN + token, json, 30, TimeUnit.MINUTES);



        UserInfoVo userInfoVo = BeanUtil.copyProperties(user, UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(token, userInfoVo);
        // 将信息返回给前端
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(Result.success(blogUserLoginVo)));


    }
}
