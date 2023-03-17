package com.xiaohe.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.entity.Role;
import com.xiaohe.service.RoleService;
import com.xiaohe.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-17 19:31
 */
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/{id}")
    public Result role(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.success(role);
    }


    @GetMapping("/list")
    public Result list(Integer pageNum, Integer pageSize, String roleName, String status) {
        PageVo pageVo = roleService.selectPage(pageNum, pageSize, roleName, status);
        return Result.success(pageVo);
    }

    /**
     * 改变角色状态
     * @param roleId
     * @param status
     * @return
     */
    @PutMapping("/changeStatus")
    public Result changeStatus(@RequestBody Long roleId, @RequestBody String status) {

//        if ("1".equals(id)) {
//            return Result.error("不允许修改管理员");
//        }
//        Long roleId = Long.valueOf(id);
        boolean b = roleService.updateStatus(Long.valueOf(roleId), status);
        if (b) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }


    @GetMapping("/roleMenuTreeselect/{id}")
    public Result roleMenuTree(@PathVariable("id") Long id) {
        // TODO 菜单树还没做
        return null;
    }


    @DeleteMapping("/{id}")
    public Result deleteRole(@PathVariable("id") Long id) {
        boolean b = roleService.removeById(id);
        if (b) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    @GetMapping("/listAllRole")
    public Result getAllRole() {
        LambdaQueryWrapper<Role> lambda = new LambdaQueryWrapper<>();
        lambda.eq(Role::getStatus,"0");
        List<Role> list = roleService.list(lambda);
        return Result.success(list);
    }



}
