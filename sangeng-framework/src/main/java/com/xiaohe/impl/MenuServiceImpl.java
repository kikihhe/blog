package com.xiaohe.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.Menu;
import com.xiaohe.mapper.MenuMapper;
import com.xiaohe.mapper.RoleMapper;
import com.xiaohe.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-10 20:38
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<String> getAccessPathByUserId(Long id) {
        // 1. 根据用户id查询他的权限



        // 2. 根据权限查询能访问的路径



    }
}
