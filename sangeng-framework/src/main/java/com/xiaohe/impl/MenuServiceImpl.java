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
import org.springframework.util.StringUtils;


import java.util.*;
import java.util.stream.Collectors;

import static java.awt.SystemColor.menu;

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
     * 返回所有菜单, 根据父id和orderNum排序
     *
     * @param status
     * @param menuName
     * @return
     */
    @Override
    public List<Menu> getMenus(Integer status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //menuName模糊查询
        queryWrapper.like(StringUtils.hasText(menuName),Menu::getMenuName,menuName);
        queryWrapper.eq(!Objects.isNull(status), Menu::getStatus,status);
        //排序 parent_id和order_num
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);;
        return menus;
    }

    @Override
    public boolean addMenu(Menu menu) {
        // 新增菜单功能
        // 上级目录与本级目录的名称不能相同
        Menu parentMenu = menuMapper.selectById(menu.getParentId());
        if (parentMenu.getMenuName().equals(menu.getMenuName())) {
            throw new RuntimeException("菜单名称不能与父级菜单的名称相同");
        }
        // 菜单名称不能重复
        // 菜单路由地址不能重复
        // 菜单组件路径不能重复, 菜单权限字符可以重复，因为一个权限可以操作多个菜单

        Menu a = menuMapper.select(menu.getMenuName(), menu.getPath());
        if (!Objects.isNull(a)) {
            throw new RuntimeException("菜单名称或菜单路由地址重复!");
        }
        int insert = menuMapper.insert(menu);
        return insert > 0;
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
