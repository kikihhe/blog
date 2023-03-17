package com.xiaohe.controller;

import com.xiaohe.domain.entity.Menu;
import com.xiaohe.service.MenuService;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    /**
     * 查看所有菜单
     * @param status
     * @param menuName
     * @return
     */
    @GetMapping("/list")
    public Result getAllMenu(Integer status, String menuName) {
        List<Menu> menus = menuService.getMenus(status, menuName);
        return Result.success(menus);
    }

    /**
     * 新增菜单，直接插入即可
     * @param menu
     * @return
     */
    @PostMapping
    public Result addMenu(@RequestBody Menu menu) {
        boolean save = menuService.addMenu(menu);
        if (save) {
            return Result.success("新增成功");
        } else {
            return Result.error("新增失败");
        }
    }

    /**
     * 修改菜单之前查看菜单信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getOneMenu(@PathVariable("id") Long id) {
        Menu byId = menuService.getById(id);
        if (Objects.isNull(byId)) {
            return Result.error("id为"+id+"的菜单不存在哦");
        } else {
            return Result.success(byId);
        }
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @PutMapping
    public Result updateMenu(@RequestBody Menu menu) {
        boolean b = menuService.updateMenu(menu);
        if (b) {
            return Result.success("更改成功");
        } else {
            return Result.error("更改失败");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteMenu(@PathVariable("id") Long id) {
        boolean isDelete = menuService.deleteMenu(id);
        if (!isDelete) {
            return Result.error("删除失败，请检查该目录是否有子目录");
        } else {
            return Result.success("删除成功!!!!");

        }
    }
}
