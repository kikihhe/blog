package com.xiaohe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohe.domain.entity.Comment;
import com.xiaohe.domain.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-07 14:52
 */
@Mapper
public interface CommentMapper  extends BaseMapper<Comment> {
    // 根据文章id查询所有根评论，分页查询
    public List<CommentVo> getAllRootComments(@Param("article_id") Long articleId,
                                              @Param("begin") Integer begin,
                                              @Param("page_size") Integer pageSize);

    public List<CommentVo> getChildrenComments(@Param("article_id") Long articleId);
}
