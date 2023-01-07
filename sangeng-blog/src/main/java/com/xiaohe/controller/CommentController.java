package com.xiaohe.controller;

import com.xiaohe.domain.vo.CommentPageVo;
import com.xiaohe.domain.vo.CommentVo;
import com.xiaohe.service.CommentService;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-07 14:57
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    // 查询文章的评论列表
    @GetMapping("/commentList")
    public Result commentList(Long articleId, Integer pageNum, Integer pageSize) {
        // 起始下标
        Integer begin = (pageNum - 1) * pageSize;
        List<CommentVo> comments = commentService.getComments(articleId, begin, pageSize);
        CommentPageVo commentPageVo = new CommentPageVo(comments);

        return Result.success(commentPageVo);
    }
}
