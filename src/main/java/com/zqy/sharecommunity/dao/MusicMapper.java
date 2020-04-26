package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface MusicMapper {

    int insert(Music music);

    List<Music> selectMusic(@Param("offset")int offset,@Param("limit")int limit);

    //模糊查询
    List<Music> selectMusicBySelective(@Param("title")String title);

    //修改状态
    int updateMusicStatus(@Param("status")int status,@Param("id")int id);

    int selectMusicCount();

    Music selectMusicById(int id);

}