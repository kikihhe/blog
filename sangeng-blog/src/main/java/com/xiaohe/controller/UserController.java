package com.xiaohe.controller;

import cn.hutool.core.bean.BeanUtil;

import com.xiaohe.domain.entity.LoginUser;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.RegisterUserVo;
import com.xiaohe.domain.vo.UserInfoVo;
import com.xiaohe.service.UserService;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-08 19:01
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    /**
     * 显示个人信息
     *
     */
    @GetMapping("/userInfo")
    public Result userInfo(HttpServletRequest request) {
        User user = ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Long id = user.getId();

        User byId = userService.getById(id);
        UserInfoVo userInfoVo = BeanUtil.copyProperties(byId, UserInfoVo.class);

        return Result.success(userInfoVo);
    }

    @PutMapping("/userInfo")
    public Result updateUserInfo(@RequestBody User user) {
        boolean b = userService.updateById(user);
        if (!b) {
            return Result.error("更新失败!");
        } else {
            return Result.success("过更新成功!");
        }

    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterUserVo user) {
        // TODO 用户注册功能还未实现

        return null;
    }


}
