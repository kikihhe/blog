package com.xiaohe.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.entity.Role;
import com.xiaohe.mapper.RoleMapper;
import com.xiaohe.service.RoleService;
import io.jsonwebtoken.lang.Strings;
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

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    @Override
    public PageVo selectPage(Integer pageNum, Integer pageSize, String roleName, String status) {
        // 分页查询
        Page<Role> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        LambdaQueryWrapper<Role> lambda = new LambdaQueryWrapper<>();
        // 状态 启用 停用
        lambda.eq(Strings.hasText(status), Role::getStatus, status);
        // 角色名模糊查询
        lambda.like(Strings.hasText(roleName), Role::getRoleName, roleName);
        // 根据排序
        lambda.orderByAsc(Role::getRoleSort);
        Page<Role> page1 = page(page, lambda);
        return new PageVo(page1.getRecords(), page1.getTotal());
    }

    /**
     * 根据id修改角色的状态: 可用 不可用
     * @param roleId
     * @param status
     * @return
     */
    @Override
    public boolean updateStatus(Long roleId, String status) {
        int update = roleMapper.updateStatus(roleId, status);
        return update > 0;
    }
}
