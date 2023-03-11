package com.xiaohe.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.Role;
import com.xiaohe.mapper.RoleMapper;
import com.xiaohe.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-10 20:55
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> getRoleKeyByUserId(Long id) {
        // 如果是管理员
        if(id == 1L) {
            ArrayList<String> roleKey = new ArrayList<>();
            roleKey.add("admin");
            return roleKey;
        }
        // 如果不是role
        return roleMapper.selectRoleKeyByUserId(id);


    }
}
