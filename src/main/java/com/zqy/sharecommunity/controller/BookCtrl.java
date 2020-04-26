package com.zqy.sharecommunity.controller;

import com.zqy.sharecommunity.entity.Book;
import com.zqy.sharecommunity.entity.DownloadUrl;
import com.zqy.sharecommunity.service.BookService;
import com.zqy.sharecommunity.util.CommunityUtil;
import com.zqy.sharecommunity.util.SpiderChapterContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zqy
 * @Date 2020/04/01
 */
@Controller
@RequestMapping("/book")
public class BookCtrl {

    @Autowired
    private BookService bookService;
    @Autowired
    private SpiderChapterContent spiderChapterContent;

    @GetMapping("/all")
    public String getBookPage(Model model){
        List<Book> books = bookService.findBooks();
        List book1=new ArrayList();
        List book2=new ArrayList();
        List book3=new ArrayList();
        List book4=new ArrayList();
        List book5=new ArrayList();
        for (Book book:books){
            if(book.getBookMark().equals("四大名著")){
                book1.add(book);
            }else if (book.getBookMark().equals("二十四史")){
                book2.add(book);
            }else if (book.getBookMark().equals("四书")){
                book3.add(book);
            }else if(book.getBookMark().equals("五经")){
                book4.add(book);
            }else if(book.getBookMark().equals("演义小说")){
                book5.add(book);
            }
        }

        model.addAttribute("book1",book1);
        model.addAttribute("book2",book2);
        model.addAttribute("book3",book3);
        model.addAttribute("book4",book4);
        model.addAttribute("book5",book5);

        return "/site/book";
    }


    @GetMapping("/detail/{bookId}")
    public String getBookInfoPage(@PathVariable("bookId")int bookId,Model model){
        Book book = bookService.findBookById(bookId);
        model.addAttribute("book",book);

        //获取目录及下载链接
        List<DownloadUrl> urls = bookService.findDownloadUrl(bookId);
        model.addAttribute("urls",urls);

        return "/site/book-attr";

    }

    @GetMapping("/chapter/{chapterId}")
    public String getChapterContent(@PathVariable("chapterId")int chapterId,Model model){

        DownloadUrl url = bookService.findDownloadUrlById(chapterId);

        String content = spiderChapterContent.getBookContent(url.getDownloadUrl());

        if (url.getBookId() == null){
            url.setId(url.getId()+1);
            url=bookService.findDownloadUrlById(url.getId());
            content = spiderChapterContent.getBookContent(url.getDownloadUrl());
        }


        Book book = bookService.findBookById(url.getBookId());

        model.addAttribute("url",url);
        model.addAttribute("content",content);
        model.addAttribute("book",book);

        return "/site/book-content";
    }


    //查询上一章
    @GetMapping("/chapter/getPre")
    @ResponseBody
    public String getPre(int chapterId){

        DownloadUrl preChapter = bookService.findPreChapterById(chapterId);
        Map<String,Object> map=new HashMap<>();
        map.put("preChapter",preChapter.getId());

        return CommunityUtil.getJSONString(0,"查询成功！",map);
    }


    //查询下一章
    @GetMapping("/chapter/getNext")
    @ResponseBody
    public String getNext(int chapterId){

        DownloadUrl nextChapter = bookService.findNextChapterById(chapterId);
        Map<String,Object> map=new HashMap<>();
        map.put("nextChapter",nextChapter.getId());
        return CommunityUtil.getJSONString(0,"查询成功！",map);
    }


}
