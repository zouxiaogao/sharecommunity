package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.VideoAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VideoAttrMapper {

    int insert(VideoAttr videoAttr);
    VideoAttr selectVideoAttr(@Param("videoId") int videoId);


}