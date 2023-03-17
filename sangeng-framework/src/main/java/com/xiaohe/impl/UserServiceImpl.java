package com.xiaohe.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.AddUserVo;
import com.xiaohe.mapper.UserMapper;
import com.xiaohe.service.UserService;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-08 19:08
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * 根据条件查询所有符合条件的用户
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param phonenumber
     * @return
     */
    @Override
    public PageVo getAllUser(Integer pageNum, Integer pageSize, String userName, String phonenumber) {
        LambdaQueryWrapper<User> lambda = new LambdaQueryWrapper<>();
        lambda.like(Strings.hasText(userName), User::getUserName, userName);
        lambda.like(Strings.hasText(phonenumber), User::getPhoneNumber, phonenumber);
        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Page<User> page1 = page(page, lambda);
        PageVo pageVo = new PageVo();
        pageVo.setTotal(page1.getTotal());
        pageVo.setRows(page1.getRecords());
        return pageVo;
    }

    /**
     * 新增用户
     * @param addUserVo
     * @return
     */
    @Override
    @Transactional
    public boolean addUser(AddUserVo addUserVo) {
        // 用户名、手机号、邮箱之前不能重复
        LambdaQueryWrapper<User> lambda = new LambdaQueryWrapper<>();
        lambda.eq(User::getUserName, addUserVo.getUserName()).or()
                .eq(User::getPhoneNumber, addUserVo.getPhoneNumber()).or()
                .eq(User::getEmail, addUserVo.getEmail());
        List<User> list = list(lambda);
        if (list.size() > 0) {
            throw new RuntimeException("该账号已注册，请勿重复注册!");
        }
        // 开始插入
        // 首先插入用户表
        User user = new User();
        BeanUtils.copyProperties(addUserVo, user);
        int insert = userMapper.insert(user);
        if (insert <= 0) {
            throw new RuntimeException("新增失败");
        }
        // 将用户与角色关系插入
        Integer a = userMapper.insertUserRole(user.getId(), addUserVo.getRoleIds());
        if (!a.equals(addUserVo.getRoleIds().size())) {
            log.error("少插入几条,失败条数为:{}", addUserVo.getRoleIds().size() - a);
        }
        return a.equals(addUserVo.getRoleIds().size());

    }
}
