package com.xiaohe.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.constants.Constants;
import com.xiaohe.domain.entity.Menu;
import com.xiaohe.domain.entity.Role;
import com.xiaohe.mapper.MenuMapper;
import com.xiaohe.mapper.RoleMapper;
import com.xiaohe.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-10 20:38
 */
@Service
@Transactional
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;



    @Override
    public List<String> getAccessPathByUserId(Long id) {
        // 如果是管理员，返回所有权限
        if (id == 1) {
            LambdaQueryWrapper<Menu> lambda = new LambdaQueryWrapper<>();
            lambda.in(Menu::getMenuType, "C", "F");
            lambda.eq(Menu::getStatus, Constants.User.SIMPLE_STATUS);
            List<Menu> list = list(lambda);
            return list.stream().map(Menu::getPerms).collect(Collectors.toList());
        }
        // 根据用户id查询角色信息
        List<Role> roles = roleMapper.selectRoleByUserId(id);
        Role role = roles.get(0);

        // 2. 根据角色id查询能访问的路径(权限)
        return menuMapper.selectPermissionByRoleId(role.getId());
    }

    @Override
    public List<Menu> getRouterByUserId(Long id) {
        List<Role> roles = roleMapper.selectRoleByUserId(id);
        Long roleId = roles.get(0).getId();
        // 根据roleid查到menuid
        List<Integer> list =  menuMapper.getMenuIdByRoleId(roleId);
        // 根据menuid查到menu
        List<Menu> menus =  menuMapper.getMenuById(list);

////
//        List<Menu> menus = menuMapper.selectMenusByRoleId(roleId);
//        List<Menu> OneMenus = new ArrayList<>();
//        menus.forEach(menu -> {
//            if(menu.getParentId() == 0)
//        });
        // 将这些menus变化为树形结构
//        List<Menu> menus = menuMapper.selectMenusByUserId(id);
        Map<Long, Menu> map = new HashMap<>();
        menus.forEach(menu -> {
            map.put(menu.getId(), menu);
        });
        menus.forEach(menu -> {
            // 证明有父路由
            if (Objects.isNull(map.get(menu.getParentId()))) {
                map.get(menu.getParentId()).getChildren().add(menu);
            }
        });
        return menus;
    }

}
