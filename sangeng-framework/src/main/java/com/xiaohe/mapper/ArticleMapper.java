package com.xiaohe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohe.domain.entity.Article;
import com.xiaohe.domain.vo.ArticleDetailVo;
import com.xiaohe.domain.vo.ArticleListVo;
import com.xiaohe.domain.vo.ArticleVo;
import com.xiaohe.domain.vo.HotArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-02 20:43
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    public List<HotArticle> getArticleByViewCount();

    public List<ArticleListVo> getArticle(@Param("begin") Integer begin,
                                          @Param("pageSize") Integer pageSize,
                                          @Param("categoryId") Long categoryId);


    public ArticleDetailVo getArticleDetail(@Param("id") Long id);

    public void updateViewCount(@Param("id")Long id,
                                @Param("viewCount") Long viewCount);

}
