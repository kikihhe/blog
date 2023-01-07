package com.xiaohe.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.domain.entity.Comment;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.CommentVo;
import com.xiaohe.mapper.CommentMapper;
import com.xiaohe.mapper.UserMapper;
import com.xiaohe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-07 14:54
 */
@Service
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<CommentVo> getComments(Long articleId, Integer begin, Integer pageSize) {
        // 先根据articleId查询对应的根评论
        List<CommentVo> rootComments = commentMapper.getAllRootComments(articleId, begin, pageSize);
        Map<Long, CommentVo> rootCommentsMap = new HashMap<>();
        for (CommentVo c: rootComments) {
            rootCommentsMap.put(c.getId(),c);
        }


        // 查询所有子评论
        List<CommentVo> childrenComments = commentMapper.getChildrenComments(articleId);
        // 查询出所有用户，为子评论的toCommentUserName赋值
        List<User> users = userMapper.getAllIdAndName();
        HashMap<Long, String> userList = new HashMap<>();
        for (User u : users) {
            userList.put(u.getId(), u.getUserName());
        }

//        childrenComments = childrenComments.stream().map(comment -> {
//            Long toId = comment.getToCommentUserId();
//            String toUsername = userList.get(toId);
//            comment.setToCommentUserName(toUsername);
//            Long rootId = comment.getRootId();
//            rootCommentsMap.get(rootId).getChildren().add(comment);
//            return comment;
//        }).collect(Collectors.toList());
//
        for (CommentVo comment : childrenComments) {
            Long toId = comment.getToCommentUserId();
            String toUsername = userList.get(toId);
            comment.setToCommentUserName(toUsername);
            Long rootId = comment.getRootId();
            // 根据子评论的root_id，获取根评论，如果根评论为空或者为一个奇特的值，(说明这是我手动在数据库中加的测试数据)直接跳过即可.
            CommentVo commentVo = rootCommentsMap.get(rootId);
            if (!Objects.isNull(commentVo)) {
                commentVo.getChildren().add(comment);
            }
        }

        return rootComments;
    }
}
