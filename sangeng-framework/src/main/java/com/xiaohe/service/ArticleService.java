package com.xiaohe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohe.domain.entity.Article;
import com.xiaohe.domain.vo.HotArticle;

import java.util.List;


/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-02 21:00
 */
public interface ArticleService extends IService<Article> {
    public List<HotArticle> getHotArticle();

}
