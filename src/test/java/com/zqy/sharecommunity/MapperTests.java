package com.zqy.sharecommunity;

import com.zqy.sharecommunity.dao.DiscussPostMapper;
import com.zqy.sharecommunity.dao.UserMapper;
import com.zqy.sharecommunity.dao.VideoMapper;
import com.zqy.sharecommunity.entity.VideoDTO;
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

    @Autowired
    private VideoMapper videoMapper;

    @Test
    public void VideoTest(){
        List<VideoDTO> videos = videoMapper.selectVideos(1,15);
        for (VideoDTO v:videos){
            System.out.println(v);
        }
    }

//    @Test
//    public void testSelectPosts(){
//        List<DiscussPost> discussPosts = postMapper.selectDiscussPosts(149);
//        for(DiscussPost post:discussPosts){
//            System.out.println(post);
//        }
//
//        int i = postMapper.selectDiscussPostRows(149);
//        System.out.println(i);
//    }




}
