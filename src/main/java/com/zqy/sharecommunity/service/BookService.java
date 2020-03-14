package com.zqy.sharecommunity.service;

import com.zqy.sharecommunity.dao.BookMapper;
import com.zqy.sharecommunity.dao.VideoDownloadUrlMapper;
import com.zqy.sharecommunity.entity.Book;
import com.zqy.sharecommunity.entity.DownloadUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zqy
 * @Date 2019/12/31
 */
@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private VideoDownloadUrlMapper videoDownloadUrl;

    public int save(Book book){
        return bookMapper.insert(book);
    }

    public int saveDownloadurl(List<DownloadUrl> books){
        return videoDownloadUrl.insert(books);
    }
}
