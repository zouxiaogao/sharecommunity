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
    List<DiscussPost> selectDiscussPosts(@Param("userId") int userId);

    //模糊查询
    List<DiscussPost> selectDiscussPostsBySelective(@Param("title") String title);

    //updateStatus
    int updatePostStatus(@Param("status")int status,@Param("id")int id);


    //查询帖子的行号
    int selectDiscussPostRows(@Param("userId")int userId);


    //新增帖子
    int insertDiscussPost(DiscussPost discussPost);


    //查看帖子详情
    DiscussPost selectDiscussPostById(int id);

    List<DiscussPost> selectAll();

    int updateCommentCount(@Param("id") int id,@Param("commentCount") int commentCount);

}