package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DiscussPostMapper {
    //查询全部帖子
    List<DiscussPost> selectDiscussPosts(@Param("userId") int userId,@Param("offset")int offset,@Param("limit")int limit);

    //查询帖子的行号
    int selectDiscussPostRows(@Param("userId")int userId);


    List<DiscussPost> selectAll();

}