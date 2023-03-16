package com.xiaohe.controller;

import com.xiaohe.domain.entity.Category;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.entity.Tag;
import com.xiaohe.domain.vo.AddArticleVo;

import com.xiaohe.service.ArticleService;
import com.xiaohe.service.CategoryService;
import com.xiaohe.service.TagService;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ArticleService articleService;


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

    /**
     * 新增博客
     * @param articleVo
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/article")
    public Result addArticle(@RequestBody @Validated AddArticleVo articleVo) throws InterruptedException {
        boolean b = articleService.addArticle(articleVo);
        if (!b) {
            return Result.error("新增博客失败");
        }
        return Result.success("新增博客成功");
    }

    /**
     * 获取所有文章(删除的除外)
     * @param pageNum
     * @param pageSize
     * @param title
     * @param summary
     * @return
     */
    @GetMapping("/article/list")
    public Result getArticles(Integer pageNum, Integer pageSize, String title, String summary) {
        PageVo page = articleService.list(pageNum, pageSize, title, summary);
        return Result.success(page);
    }

    @PreAuthorize("@permissionService.hasPermission('test')")
    @GetMapping("/test")
    public Result testPermission() {
        return Result.success("请求成功");

    }

    @GetMapping("/article/{id}")
    public Result article(@PathVariable("id") Long id) {
        AddArticleVo addArticleVo = articleService.getArticle(id);
        return Result.success(addArticleVo);
    }

    @PutMapping
    public Result updateArticle(@RequestBody AddArticleVo addArticleVo) throws InterruptedException {
        boolean b = articleService.updateArticle(addArticleVo);
        if (b) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败!请稍后重试");
        }
    }
    @DeleteMapping("/article/{id}")
    public Result delete(@PathVariable Long id) {
        boolean b = articleService.removeById(id);
        if (b) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败，请稍后重试");
        }
    }

}
