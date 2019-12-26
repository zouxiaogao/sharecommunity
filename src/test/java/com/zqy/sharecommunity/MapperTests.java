package com.zqy.sharecommunity;

import com.zqy.sharecommunity.dao.DiscussPostMapper;
import com.zqy.sharecommunity.dao.UserMapper;
import com.zqy.sharecommunity.entity.DiscussPost;
import com.zqy.sharecommunity.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author zqy
 * @Date 2019/12/02
 */


@SpringBootTest
public class MapperTests {
    @Autowired
    private DiscussPostMapper postMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectPosts(){
        List<DiscussPost> discussPosts = postMapper.selectDiscussPosts(149, 0, 10);
        for(DiscussPost post:discussPosts){
            System.out.println(post);
        }

        int i = postMapper.selectDiscussPostRows(149);
        System.out.println(i);
    }



    @Test
    public void testUserSelect(){
        User user = userMapper.selectByUserId(149);
        System.out.println(user.toString());
    }
}
