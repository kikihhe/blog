package com.xiaohe.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.dto.UserDto;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.entity.Role;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.AddUserVo;
import com.xiaohe.domain.vo.UserVo;
import com.xiaohe.mapper.RoleMapper;
import com.xiaohe.mapper.UserMapper;
import com.xiaohe.service.UserService;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private RoleMapper roleMapper;




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
                .eq(User::getPhoneNumber, addUserVo.getPhonenumber()).or()
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

    /**
     * 查用户关联的角色的id
     * @param id
     * @return
     */
    @Override
    @Transactional
    public UserVo getUserBeforeUpdate(Long id) {
        // 通过id获取用户关联的所有角色
        List<Role> roles = roleMapper.selectRoleByUserId(id);
        // 将角色id提取出来
        List<Long> roleIds = new ArrayList<>();
        roles.forEach(role -> {
            roleIds.add(role.getId());
        });
        // 查询出所有状态正常的角色
        LambdaQueryWrapper<Role> lambda = new LambdaQueryWrapper<>();
        lambda.eq(Role::getStatus, "0");
        List<Role> roleList = roleMapper.selectList(lambda);
        // 查询出用户信息
        User user = userMapper.selectById(id);
        UserVo userVo = new UserVo();
        UserDto userDto = new UserDto();
        // 封装为addUserVo便于返回
        BeanUtils.copyProperties(user, userDto);
        userVo.setUser(userDto).setRoles(roleList).setRoleIds(roleIds);
        return userVo;
    }

    /**
     * 更新user信息, 先删除后增加
     * @param user
     * @return
     */
    @Override
    @Transactional
    public boolean updateUser(AddUserVo user) {
        // 先删除用户信息与用户角色关联信息
        UserService userService = (UserService) AopContext.currentProxy();
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(user.getId());
        boolean b1 = userService.removeUser(ids);
        // 再新增
        boolean b = userService.addUser(user);


        return b1 && b;
    }

    /**
     * 批量删除用户，首先批量删除用户信息表，再删除用户角色关联表
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public boolean removeUser(List<Long> ids) {
        // 先删除用户表
        int i = userMapper.deleteBatchIds(ids);
        if (i != ids.size()) {
            log.error("部分用户删除失败");
            return false;
        }
        // 再删除用户与角色关联表, 只要id在ids里面的都删除
        int j = userMapper.deleteUserAndRole(ids);
        if (j != ids.size()) {
            log.error("用户与角色关联表没删干净");
            return false;
        }
        return true;
    }
}
