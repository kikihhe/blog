package com.xiaohe.impl;

import com.xiaohe.domain.LoginUser;
import com.xiaohe.domain.entity.User;
import com.xiaohe.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-06 17:09
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    // 根据用户名查询用户是否存在
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(s);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名/密码错误");
        }
        return new LoginUser(user);
    }
}
