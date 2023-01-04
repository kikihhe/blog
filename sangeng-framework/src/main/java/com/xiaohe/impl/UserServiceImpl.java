package com.xiaohe.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.User;
import com.xiaohe.mapper.UserMapper;
import com.xiaohe.service.UserService;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}
