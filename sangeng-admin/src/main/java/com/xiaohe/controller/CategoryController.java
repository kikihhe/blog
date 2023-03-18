package com.xiaohe.controller;

import com.xiaohe.domain.entity.Category;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.vo.AddCategoryVo;
import com.xiaohe.domain.vo.UpdateCategoryVo;
import com.xiaohe.service.CategoryService;
import com.xiaohe.utils.IdUtil;
import com.xiaohe.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 添加分类
     * @param addCategoryVo
     * @return
     */
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

    /**
     * 修改分类前根据id查找分类的信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getCategoryById(@PathVariable("id") Long id) {
        Category byId = categoryService.getById(id);
        return Result.success(byId);
    }


    /**
     * 更改分类
     * @param category
     * @return
     */
    @PutMapping
    public Result updateCategory(@Validated UpdateCategoryVo category) {
        boolean b = categoryService.updateCategory(category);
        if(b) {
            return Result.success("更改成功");
        } else {
            return Result.error("更改失败");
        }
    }

    @DeleteMapping
    public Result updateCategory(String id) {
        List<Long> ids = IdUtil.stringCastToList(id);
        boolean b = categoryService.daleteCategorys(ids);
        if (b) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }


}
