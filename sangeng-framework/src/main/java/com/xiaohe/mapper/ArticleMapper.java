package com.xiaohe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohe.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-02 20:43
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
