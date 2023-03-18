package com.xiaohe.controller;

import com.xiaohe.domain.entity.Category;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.vo.AddCategoryVo;
import com.xiaohe.service.CategoryService;
import com.xiaohe.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-18 12:34
 */
@RestController
@Slf4j
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result pageList(Integer pageNum, Integer pageSize, String name, String status) {
        PageVo pageVo = categoryService.selectPageList(pageNum, pageSize, name, status);
        return Result.success(pageVo);
    }
    @PostMapping
    public Result addCategory(@RequestBody @Validated AddCategoryVo addCategoryVo) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryVo, category);
        boolean b = categoryService.addCategory(category);
        if (b) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }
}
