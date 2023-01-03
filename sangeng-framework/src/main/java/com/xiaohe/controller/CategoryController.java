package com.xiaohe.controller;

import com.xiaohe.domain.entity.Category;
import com.xiaohe.service.CategoryService;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-03 13:26
 */
@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    @GetMapping("/getCategoryList")
    public Result getCategoryList() {
        List<Category> list = categoryService.getCategoryList();
        return Result.success(list);
    }
}
