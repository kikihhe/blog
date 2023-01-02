package com.xiaohe.controller;

import com.xiaohe.domain.entity.Article;
import com.xiaohe.service.ArticleService;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-02 21:02
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/getAllArticle")
    public Result getAllArticle() {
        List<Article> list = articleService.list();
        return Result.success(list);
    }
}
