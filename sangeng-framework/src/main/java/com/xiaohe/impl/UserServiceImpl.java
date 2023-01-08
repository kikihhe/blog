package com.xiaohe.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.User;
import com.xiaohe.mapper.UserMapper;
import com.xiaohe.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-08 19:08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
