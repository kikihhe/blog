package com.xiaohe.controller;

import com.xiaohe.domain.entity.Category;
import com.xiaohe.domain.entity.Tag;
import com.xiaohe.service.CategoryService;
import com.xiaohe.service.TagService;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-16 13:28
 */
@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    public CategoryService categoryService;
    @Autowired
    public TagService tagService;


    /**
     * 获取所有分类 /category/listAllCategory
     * @return
     */
    @GetMapping("/category/listAllCategory")
    public Result listAllCategory() {
        List<Category> categoryList = categoryService.getCategoryList();
        if (!Objects.isNull(categoryList)) {
            return Result.success(categoryList);
        } else {
            return Result.success("没有分类");
        }
    }

    /**
     * 获取所有标签
     * @return
     */
    @GetMapping("/tag/listAllTag")
    public Result listAllTag() {
        List<Tag> list = tagService.list();
        if (Objects.isNull(list)) {
            return Result.success("没有标签");
        } else {
            return Result.success(list);
        }
    }
}
