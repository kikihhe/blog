package com.xiaohe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohe.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-10 20:37
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据角色信息查询per
     * @param id
     * @return
     */
    public List<String> selectPermissionByRoleId(@Param("id") Long id);


    List<Menu> selectMenusByRoleId(@Param("roleId") Long roleId);

    List<Integer> getMenuIdByRoleId(@Param("roleId") Long roleId);


    List<Menu> getMenuById(@Param("list") List<Integer> list);
}
