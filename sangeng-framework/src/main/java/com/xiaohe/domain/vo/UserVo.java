package com.xiaohe.domain.vo;

import com.xiaohe.domain.dto.UserDto;
import com.xiaohe.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-18 11:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserVo {
    /**
     * 用户关联的所有用户的id
     */
    private List<Long> roleIds;
    /**
     * 所有的角色信息
     */
    private List<Role> roles;

    private UserDto user;
}
