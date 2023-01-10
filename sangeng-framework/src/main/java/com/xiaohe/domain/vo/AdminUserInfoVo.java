package com.xiaohe.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {

    // 允许目前登录用户访问的所有路径
    private List<String> permissions;

    // 目前登录用户拥有的所有权限
    private List<String> roles;

    // 当前用户的信息
    private UserInfoVo user;
}
