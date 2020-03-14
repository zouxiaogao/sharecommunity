package com.zqy.sharecommunity.service;

import com.zqy.sharecommunity.dao.DiscussPostMapper;
import com.zqy.sharecommunity.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zqy
 * @Date 2019/12/02
 */

@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> selectDiscussPosts(int userId){
        return discussPostMapper.selectDiscussPosts(userId);
    }

    public int selectDiscussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
