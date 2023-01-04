package com.xiaohe.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.BlogLoginVo;
import com.xiaohe.mapper.UserMapper;
import com.xiaohe.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-04 18:48
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LambdaQueryChainWrapper<User> lambda = new LambdaQueryChainWrapper<>(userMapper);
        // 这里有可能抛出"用户名不存在"的异常
        // 只要是登陆时用户名或者密码错误，都提示"用户名/密码错误",都抛出"密码错误!"的提示
        // 具体的提示，打印在控制台
        User user = userMapper.selectOne(lambda.eq(User::getUserName, s));
        if (Objects.isNull(user)) {
            log.error(s + "登录时账号/密码错误");
            throw new UsernameNotFoundException("用户名/密码错误!");
        }
        // TODO 查询权限


        return new BlogLoginVo(user);
    }
}
