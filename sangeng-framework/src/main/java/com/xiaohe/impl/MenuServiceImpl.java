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
        List<Menu> list = null;
        if (id == 1) {
           list = menuMapper.selectAllRouters();
        } else {
            list = menuMapper.selectRoutersByUserId(id);
        }
        return buildMenuTree(list);

    }

    /**
     * 根据menu列表返回树形menu
     * @param list
     * @return
     */
    private List<Menu> buildMenuTree(List<Menu> list) {
        List<Menu> menus = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getParentId().equals(0L)) {
                menus.add(list.get(i));
            }
        }
        for (int i = 0; i < menus.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getParentId().equals(menus.get(i).getId())) {
                    menus.get(i).getChildren().add(list.get(j));
                }
            }
        }
        return menus;
    }

}
