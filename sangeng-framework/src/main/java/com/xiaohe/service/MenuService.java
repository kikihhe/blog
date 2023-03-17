package com.xiaohe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohe.domain.entity.Menu;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-10 20:37
 */
public interface MenuService extends IService<Menu> {
    /**
     * 根据用户的id查询他能访问的所有路径
     * @param id 用户id
     * @return 返回该用户能访问的所有路径集合
     */
    public List<String> getAccessPathByUserId(Long id);

    /**
     * 通过用户id查找它可以看到的路由
     * @param id
     * @return
     */
    public List<Menu> getRouterByUserId(Long id);

    public List<Menu> getMenus(Integer status, String menuName);

    public boolean addMenu(Menu menu);

    public boolean updateMenu(Menu menu);

    public boolean deleteMenu(Long id);
}
