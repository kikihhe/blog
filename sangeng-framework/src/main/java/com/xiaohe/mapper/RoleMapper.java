package com.xiaohe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohe.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-10 20:54
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    public List<Role> selectRoleByUserId(@Param("id") Long id);

    List<String> selectRoleKeyByUserId(@Param("id") Long id);
}
