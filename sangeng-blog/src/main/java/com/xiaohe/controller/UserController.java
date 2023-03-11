package com.xiaohe.controller;

import cn.hutool.core.bean.BeanUtil;

import com.xiaohe.domain.LoginUser;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.RegisterUserVo;
import com.xiaohe.domain.vo.UserInfoVo;
import com.xiaohe.service.UserService;
import com.xiaohe.utils.Result;
import com.xiaohe.utils.VerifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-08 19:01
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * 显示个人信息
     *
     */
    @GetMapping("/userInfo")
    public Result userInfo() {
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
    public Result register(@RequestBody @Validated RegisterUserVo user) {
        if (!VerifyUtils.isPasswordLegal(user.getPassword())) {
            return Result.error("密码不符合格式!请输入6-12位数字、字母、下划线");
        }
        User user1 = BeanUtil.copyProperties(user, User.class);
        // 对密码进行加密
        user1.setPassword(bCryptPasswordEncoder.encode(user1.getPassword()));
        // 添加，如果添加失败，报异常就可以用全局异常处理，如果不报异常，可以直接返回添加失败的信息
        boolean save = userService.save(user1);

        if (save) {
            return Result.success("操作成功!");
        } else {
            log.error("添加{}失败", user);
            return Result.error("操作失败!");
        }

    }


}
