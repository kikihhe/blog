package com.xiaohe.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.Article;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.entity.Tag;
import com.xiaohe.domain.vo.*;
import com.xiaohe.mapper.ArticleMapper;
import com.xiaohe.mapper.TagMapper;
import com.xiaohe.service.ArticleService;
import com.xiaohe.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-02 21:01
 */
@Service
@Transactional
@Async("threadPoolExecutor")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

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

    /**
     * 发布文章
     * @param addArticleVo
     * @return
     */
    @Transactional
    @Override
    public boolean addArticle(AddArticleVo addArticleVo) throws InterruptedException {
        Article article = new Article();
        BeanUtils.copyProperties(addArticleVo, article);
        // 将博客插入到文章表中
        int insert = articleMapper.insert(article);
        if (insert <= 0) {
            throw new RuntimeException("新增博客失败");
        }
        // 将文章与标签的关系插入到article_tag表中
//        tagMapper.addArticleTag(article.getId(),);
        List<Long> tags = addArticleVo.getTags();
        CountDownLatch countDownLatch = new CountDownLatch(tags.size());
        for (int i = 0; i < tags.size(); i++) {
            Long tagId = tags.get(i);
            threadPoolExecutor.execute(() -> {
                int i1 = tagMapper.addArticleTag(article.getId(), tagId);
                // TODO 文章与标签的关系插入时可能会出现没插进去的情况，不管是什么原因，放入rabbitmq，rabbitmq处理
                if (i1 < 0) {
                    log.error("文章与标签的关系插入失败");
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        return true;
    }

    /**
     * 获取所有文章列表，删除的除外
     * @param begin
     * @param pageSize
     * @param title
     * @param summary
     * @return
     */
    @Override
    public PageVo list(Integer begin, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> lambda = new LambdaQueryWrapper<>();
        lambda.like(StringUtils.hasText(title), Article::getTitle, title);
        lambda.like(StringUtils.hasText(summary), Article::getSummary, summary);
        Page<Article> page = new Page<>();
        page.setCurrent(begin);
        page.setSize(pageSize);
        Page<Article> result = page(page, lambda);
        PageVo pageVo = new PageVo();
        pageVo.setRows(result.getRecords());
        pageVo.setTotal(result.getTotal());
        return pageVo;

    }

    /**
     * 通过id获取文章详情信息，包含: 标签
     * @param id
     * @return
     */
    @Override
    public AddArticleVo getArticle(Long id) {
        Article article = articleMapper.selectById(id);
        AddArticleVo addArticleVo = new AddArticleVo();
        BeanUtils.copyProperties(article, addArticleVo);
        // 根据id 查找标签

        List<Long> list = tagMapper.selectTagListByArticleId(id);
        addArticleVo.setTags(list);
        return addArticleVo;

    }

    @Override
    @Transactional
    public boolean updateArticle(AddArticleVo addArticleVo) throws InterruptedException {
        Article article = new Article();
        BeanUtils.copyProperties(addArticleVo, article);

        int update = articleMapper.updateById(article);
        if (update <= 0) {
            throw new RuntimeException("更改失败");
        }
        // 先将关系删除，再插入

        Integer integer = tagMapper.deleteTagsByArticleId(addArticleVo.getId());
        // 再将关系插入到表中
        List<Long> tags = addArticleVo.getTags();
        CountDownLatch countDownLatch = new CountDownLatch(tags.size());
        for (int i = 0; i < tags.size(); i++) {
            Long tagId = tags.get(i);
            threadPoolExecutor.execute(() -> {
                int i1 = tagMapper.addArticleTag(article.getId(), tagId);
                if (i1 < 0) {
                    log.error("文章与标签的关系插入失败");
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        return true;
    }
}

