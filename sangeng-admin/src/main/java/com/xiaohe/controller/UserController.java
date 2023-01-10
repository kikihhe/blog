package com.xiaohe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaohe.constants.Constants;
import com.xiaohe.domain.entity.LoginUser;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.LoginUserVo;
import com.xiaohe.utils.JWTUtils;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-10 13:30
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result login(@RequestBody LoginUserVo user) throws JsonProcessingException {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (Objects.isNull(authenticate) || Objects.isNull(authentication.getPrincipal())) {
            return Result.error("用户名/密码错误");
        }
        ObjectMapper objectMapper = new ObjectMapper();

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User user1 = loginUser.getUser();
        user1.setPassword(null);
        // 根据用户信息生成token
        // token为键，json为值 存入redis
        String json = objectMapper.writeValueAsString(user1);
        String token = JWTUtils.generateToken(objectMapper.readValue(json, Map.class));
        // 存入redis，以 token-json形式存储, token存活时间为30mins
        stringRedisTemplate.opsForValue().set(Constants.User.ADMIN_LOGIN_TOKEN + token, json, 30, TimeUnit.MINUTES);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 将token返回
        Map<String, String> map = new HashMap<>();
        map.put(Constants.User.AUTHENTICATION_NAME, token);
        return Result.success(map, "登录成功");
    }
}
