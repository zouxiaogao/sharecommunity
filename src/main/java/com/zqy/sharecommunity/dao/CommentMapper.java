package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    //根据实体（帖子）查询评论
    List<Comment> selectCommentsByEntity(@Param("entityType") int entityType, @Param("entityId")int entityId);

    //根据实体（帖子）获取评论条数
    int selectCountByEntity(@Param("entityType")int entityType, @Param("entityId")int entityId);

    //增加评论
    int insertComment(Comment comment);

    //根据id查询评论信息
    Comment selectCommentById(int id);
}