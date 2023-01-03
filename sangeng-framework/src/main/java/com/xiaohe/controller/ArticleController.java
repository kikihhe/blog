package com.xiaohe.controller;

import com.xiaohe.domain.entity.Article;
import com.xiaohe.domain.vo.HotArticle;
import com.xiaohe.service.ArticleService;
import com.xiaohe.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-02 21:02
 */
@Slf4j
@CrossOrigin // 允许跨域
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


    /**
     * 获取热门文章列表：按照访问量排序的前10个
     * HotArticle:
     * Long id
     * String title
     * Long viewCount
     */
    @GetMapping("/hotArticleList")
    public Result hotArticleList() {
        List<HotArticle> hotArticles = articleService.getHotArticle();

        return Result.success(hotArticles);
    }
}
