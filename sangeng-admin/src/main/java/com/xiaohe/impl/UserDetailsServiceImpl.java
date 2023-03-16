package com.xiaohe.impl;

import com.xiaohe.constants.Constants;
import com.xiaohe.domain.entity.LoginUser;
import com.xiaohe.domain.entity.User;
import com.xiaohe.mapper.MenuMapper;
import com.xiaohe.mapper.UserMapper;
import com.xiaohe.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuService menuService;
    // 根据用户名查询用户是否存在
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(s);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名/密码错误");
        }
        // 查询权限信息, 如果是后台用户，查询权限。如果是前台用户，不需要查
        List<String> list = new ArrayList<>();
        if (user.getType().equals(Constants.User.SYSTEM_ADMIN_TYPE)) {
            // 权限
            list = menuMapper.selectPermsByUserId(user.getId());

        }

        return new LoginUser(user, list);
    }
}
