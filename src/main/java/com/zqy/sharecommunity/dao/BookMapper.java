package com.zqy.sharecommunity.dao;

import com.zqy.sharecommunity.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper {

    int insert(Book record);

    List<Book> selectBooks();

    //模糊查询
    List<Book> selectBooksBySelective(@Param("title")String title,@Param("offset") int offset,@Param("limit") int limit);

    //修改状态
    int updateBookStatus(@Param("status")int status, @Param("id")int id);

    Book selectBookById(int bookId);



}