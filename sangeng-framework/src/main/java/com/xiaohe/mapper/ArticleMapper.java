package com.xiaohe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohe.domain.entity.Article;
import com.xiaohe.domain.vo.HotArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-02 20:43
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    public List<HotArticle> getArticleByViewCount();
}