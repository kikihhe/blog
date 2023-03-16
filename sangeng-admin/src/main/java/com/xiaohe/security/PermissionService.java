package com.xiaohe.security;

import com.xiaohe.domain.entity.LoginUser;
import com.xiaohe.domain.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-16 16:29
 */
@Service("permissionService")
public class PermissionService {
    public boolean hasPermission(String permission) {

        // 获取当前用户
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUser.getUser().getId().equals(1L)) {
            return true;
        }
        if (loginUser.getPermission().contains(permission)) {
            return true;
        } else {
            return false;
        }

    }
}
