package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video video);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
}