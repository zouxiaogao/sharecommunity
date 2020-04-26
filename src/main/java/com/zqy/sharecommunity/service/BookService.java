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

    public List<Book> findBooks(){
        return bookMapper.selectBooks();
    }

    public Book findBookById(int bookId){
        return bookMapper.selectBookById(bookId);
    }

    public List<DownloadUrl> findDownloadUrl(int bookId){
        return videoDownloadUrl.selectDownloadUrlByBookId(bookId);
    }

    public DownloadUrl findDownloadUrlById(int id){
        return videoDownloadUrl.selectDownloadUrlById(id);
    }

    public List<Book> findBookBySelective(String title){
        return bookMapper.selectBooks();
    }


    //上一章
    public DownloadUrl findPreChapterById(int id){
        return videoDownloadUrl.selectPreChapterById(id);
    }

    //下一章
    public DownloadUrl findNextChapterById(int id){
        return videoDownloadUrl.selectNextChapterById(id);
    }
}
