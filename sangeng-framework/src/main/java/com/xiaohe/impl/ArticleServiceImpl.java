package com.xiaohe.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.Article;
import com.xiaohe.domain.vo.ArticleDetailVo;
import com.xiaohe.domain.vo.ArticleListVo;
import com.xiaohe.domain.vo.ArticleVo;
import com.xiaohe.domain.vo.HotArticle;
import com.xiaohe.mapper.ArticleMapper;
import com.xiaohe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-02 21:01
 */
@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;


    @Override
    @Transactional
    public List<HotArticle> getHotArticle() {
        return articleMapper.getArticleByViewCount();
    }

    @Override
    public List<ArticleListVo> getArticleList(Integer begin, Integer pageSize, Long categoryId) {
        List<ArticleListVo> articles = articleMapper.getArticle(begin, pageSize, categoryId);
        return articles;
    }

    @Override
    public ArticleDetailVo article(Long id) {
        ArticleDetailVo articleDetail = articleMapper.getArticleDetail(id);
        return articleDetail;
    }

    @Override
    public void updateViewCount(Long id, Long viewCount) {
        articleMapper.updateViewCount(id, viewCount);
    }
}

