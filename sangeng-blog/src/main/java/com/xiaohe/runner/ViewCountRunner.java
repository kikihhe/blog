package com.xiaohe.runner;

import com.xiaohe.constants.Constants;
import com.xiaohe.domain.entity.Article;
import com.xiaohe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 在SpringBoot项目启动时将所有文章的浏览量存入redis
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 将所有文章的浏览量加入redis中，以hash结构存储
     */
    @Override
    public void run(String... args) throws Exception {
        List<Article> articles = articleService.list();
        HashMap<String, String> map = new HashMap<>();
        for (Article a: articles) {
            if (String.valueOf(Constants.Article.Formal_Article).equals(a.getStatus())) {
                map.put(a.getId() + "", a.getViewCount() + "");
            }
        }

        stringRedisTemplate.opsForHash().putAll(Constants.Article.BLOG_Article_VIEWCOUNT, map);
    }
}
