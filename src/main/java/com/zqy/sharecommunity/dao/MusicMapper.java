package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.Music;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface MusicMapper {

    int insert(Music music);

}