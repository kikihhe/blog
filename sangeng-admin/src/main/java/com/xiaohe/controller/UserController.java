package com.xiaohe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaohe.constants.Constants;
import com.xiaohe.domain.entity.LoginUser;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.AdminLoginUserVo;
import com.xiaohe.service.MenuService;
import com.xiaohe.service.RoleService;
import com.xiaohe.utils.JWTUtils;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@RestController
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @PostMapping("/user/login")
    public Result login(@RequestBody AdminLoginUserVo user) throws JsonProcessingException {
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


    @GetMapping("getInfo")
    public Result getInfo() {
        // 从SecurityContext中取出当前登录的用户的信息
        User user = ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        // 根据用户id查询他能访问的路径，封装为List<String>


        // 根据用户id查询他的roleKey


        return null;

    }
}
