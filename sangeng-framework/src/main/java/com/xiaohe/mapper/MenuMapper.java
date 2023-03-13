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


    /**
     * 管理员有所有routers
     * @return
     */
    public List<Menu> selectAllRouters();

    /**
     * 根据id查找routers
     * @param id
     * @return
     */
    List<Menu> selectRoutersByUserId(@Param("id") Long id);
}
