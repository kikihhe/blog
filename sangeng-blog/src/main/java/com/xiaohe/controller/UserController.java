package com.xiaohe.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiaohe.constants.Constants;
import com.xiaohe.domain.entity.LoginUser;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.UserInfoVo;
import com.xiaohe.service.UserService;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/userInfo")
    public Result userInfo(HttpServletRequest request) {
        User user = ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Long id = user.getId();

        User byId = userService.getById(id);
        UserInfoVo userInfoVo = BeanUtil.copyProperties(byId, UserInfoVo.class);

        return Result.success(userInfoVo);
    }
}
