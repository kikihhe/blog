package com.xiaohe.controller;

import com.xiaohe.domain.entity.Article;
import com.xiaohe.domain.vo.ArticleDetailVo;
import com.xiaohe.domain.vo.ArticleListVo;
import com.xiaohe.domain.vo.ArticlePageVo;
import com.xiaohe.domain.vo.HotArticle;
import com.xiaohe.service.ArticleService;
import com.xiaohe.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 查询文章，可能分页，也可能按照文章分类查询
     * @param pageNum 第几页
     * @param pageSize 每一页有多少条数据
     * @param categoryId 分类的id
     * @return
     */
    @GetMapping("/articleList")
    public Result articleList(@RequestParam("pageNum") Integer pageNum,
                              @RequestParam("pageSize") Integer pageSize,
                              @RequestParam("categoryId") Long categoryId) {
        // 使用Mysql的limit分页，计算起始下标
        Integer begin = (pageNum - 1) * pageSize;
        // 文章列表类
        List<ArticleListVo> articles = articleService.getArticleList(begin, pageSize, categoryId);
        // 封装的返回结果类，内有 文章列表集合、数据个数
        ArticlePageVo list = new ArticlePageVo(articles);
        return Result.success(list);
    }
    @GetMapping("/{id}")
    public Result article(@PathVariable("id") Long id) {
        if (id < 0) {
            return Result.error("请输入正确的参数");
        }


        ArticleDetailVo article = articleService.article(id);
        return Result.success(article);
    }


}
