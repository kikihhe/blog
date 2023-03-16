package com.xiaohe.controller;

import com.xiaohe.domain.entity.Menu;
import com.xiaohe.service.MenuService;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-16 21:45
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/list")
    public Result getAllMenu(Integer status, String menuName) {
        List<Menu> menus = menuService.getMenus(status, menuName);
        return Result.success(menus);


    }
}
