package com.xiaohe.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.Article;
import com.xiaohe.domain.vo.ArticleListVo;
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
}









//==>  Preparing:
//            select a.id, a.create_time, a.view_count, a.summary, a.thumbnail, a.title, c.name
//            from article a, category c
//            WHERE a.category_id = ? and a.category_id = c.id
//                  and a.status = 0
//            order by a.is_top desc, a.create_time desc
//            limit ?, ?;
//        ==> Parameters:
//        ==> Parameters:
//        ==> Parameters: 1(Long), 0(Integer), 10(Integer)
//        <==    Columns: id, create_time, view_count, summary, thumbnail, title, name
//        <==    Columns: id, title, view_count
//        <==    Columns: id, name, pid, description, status, create_by, create_time, update_by, update_time, del_flag
//        <==        Row: 1, SpringSecurity从入门到精通, 105
//        <==        Row: 1, java, -1, wsd, 0, null, null, null, null, 0
//        <==        Row: 1, 2022-01-23 23:20:11, 105, SpringSecurity框架教程-Spring Security+JWT实现项目级前端分离认证授权,
//                           https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/948597e164614902ab1662ba8452e106.png,
//                           SpringSecurity从入门到精通, java
//        <==        Row: 5, sdad, 44
//        <==      Total: 2
//        <==        Row: 2, PHP, -1, wsd, 0, null, null, null, null, 0
//        <==      Total: 1
//        <==      Total: 2
