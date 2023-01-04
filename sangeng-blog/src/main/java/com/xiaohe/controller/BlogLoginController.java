package com.xiaohe.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaohe.constants.Constants;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.BlogLoginVo;
import com.xiaohe.domain.vo.BlogUserLoginVo;
import com.xiaohe.domain.vo.UserInfoVo;
import com.xiaohe.service.UserService;
import com.xiaohe.utils.JWTUtils;
import com.xiaohe.utils.Result;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-04 17:53
 */
@Slf4j
@RestController
public class BlogLoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户端的登陆
     */
    @PostMapping("/login")
    public Result blogLogin(@RequestBody BlogLoginVo user) throws JsonProcessingException {
        // 将用户名/密码封装为Authentication
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        // 通过AuthenticationManager的authenticate方法，调用UserDetails的loadUserByUsername方法进行校验.
        Authentication authenticate = authenticationManager.authenticate(authentication);

        // 到这里说明已经登陆成功,
        BlogLoginVo loginVo = (BlogLoginVo) authenticate.getPrincipal();
        // user1中含有所有信息
        User user1 = loginVo.getUser();
        user1.setPassword(null);

        //  可以生成token
        // 1. 存起来放到Redis
        ObjectMapper objectMapper = new ObjectMapper();
        String tokenValue = objectMapper.writeValueAsString(loginVo);
        String tokenKey = JWTUtils.generateToken(objectMapper.readValue(tokenValue, Map.class));
        stringRedisTemplate.opsForValue().set(Constants.User.BLOG_LOGIN_TOKEN + tokenKey, tokenValue, 30, TimeUnit.MINUTES);
        // 2. 将token和用户信息返回给前端
        UserInfoVo userInfo = BeanUtil.copyProperties(user1, UserInfoVo.class);


        BlogUserLoginVo data = new BlogUserLoginVo(tokenKey, userInfo);




        return Result.success(data);
    }

}
