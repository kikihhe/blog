package com.xiaohe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohe.domain.entity.Role;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-10 20:54
 */
public interface RoleService extends IService<Role> {
    List<String> getRoleKeyByUserId(Long id);
}
