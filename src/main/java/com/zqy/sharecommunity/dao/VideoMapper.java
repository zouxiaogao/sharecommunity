package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.VideoDTO;
import com.zqy.sharecommunity.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video video);

    int insertSelective(Video record);

    Video selectvideoById(Integer id);

    List<VideoDTO> selectVideos(@Param("offset") int offset,@Param("limit") int limit);

    //模糊查询
    List<VideoDTO> selectVideosBySelective(@Param("title")String title);


    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    int selectVideoCount();

    //修改状态
    int updateVideoStatus(@Param("status")int status,@Param("id")int id);

    VideoDTO selectVideoById(int videoId);


}