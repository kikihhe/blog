package com.xiaohe.controller;

import com.xiaohe.domain.entity.Comment;
import com.xiaohe.domain.LoginUser;
import com.xiaohe.domain.vo.CommentPageVo;
import com.xiaohe.domain.vo.CommentVo;
import com.xiaohe.service.CommentService;
import com.xiaohe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

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


    @PostMapping
    public Result addComment(@RequestBody Comment comment, HttpServletRequest request) {
        // 获取创建人
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String token = request.getHeader("token");
//        if ()
        LoginUser loginUser = (LoginUser) principal;
        Long id = loginUser.getUser().getId();
        if (Objects.isNull(id) || id < 0) {
            throw new RuntimeException("未登录，请先登录再评论");
        }
        comment.setCreateBy(id);
        comment.setUpdateBy(id);

        int i = commentService.addComment(comment);
        if (i == 1) {
            return Result.success("评论成功");
        } else {
            return Result.error("评论失败,请重试");
        }
    }

    @GetMapping("/linkCommentList")
    public Result addLinkComment() {
        return null;

    }
}
