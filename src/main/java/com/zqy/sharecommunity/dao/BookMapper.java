package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BookMapper {

    int insert(Book record);

}