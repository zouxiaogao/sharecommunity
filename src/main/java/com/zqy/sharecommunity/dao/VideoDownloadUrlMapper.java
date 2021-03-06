package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.DownloadUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface VideoDownloadUrlMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(@Param("list") List<DownloadUrl> record);

    List<DownloadUrl> selectDownloadUrlByVideoId(int videoId);
    List<DownloadUrl> selectDownloadUrlByBookId(int bookId);
    DownloadUrl selectDownloadUrlById(int id);

    DownloadUrl selectPreChapterById(int id);
    DownloadUrl selectNextChapterById(int id);
}