package com.xiaohe;

import com.xiaohe.domain.entity.User;
import com.xiaohe.mapper.CommentMapper;
import com.xiaohe.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-07 16:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SanGengBlogApplication.class)
public class TestCommentMapper {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1() {

//        List<CommentVo> allRootComments = commentMapper.getAllRootComments(1L, 0, 10);
//        System.out.println(allRootComments);
        List<User> users = userMapper.getAllIdAndName();
        System.out.println(users);


    }
}
