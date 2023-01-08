package com.xiaohe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohe.domain.entity.Comment;
import com.xiaohe.domain.vo.CommentVo;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-07 14:53
 */
public interface CommentService extends IService<Comment> {
    // 展开评论列表
    public List<CommentVo> getComments(Long articleId, Integer begin, Integer pageSize);

    public int addComment(Comment comment);
}
