package com.xiaohe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohe.domain.entity.Article;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.vo.*;

import java.util.List;


/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-02 21:00
 */
public interface ArticleService extends IService<Article> {
    // 获取热门文章列表，根据浏览量排序
    public List<HotArticle> getHotArticle();

    // 获取文章列表
    public List<ArticleListVo> getArticleList(Integer begin, Integer pageSize, Long categoryId);

    // 获取文章详情
    public ArticleDetailVo article(Long id);

    public void updateViewCount(Long id, Long viewCount);

    // 发布文章
    public boolean addArticle(AddArticleVo addArticleVo) throws InterruptedException;

    public PageVo list(Integer begin, Integer pageSize, String title, String summary);

    public AddArticleVo getArticle(Long id);

    public boolean updateArticle(AddArticleVo addArticleVo) throws InterruptedException;
}
