package com.zqy.sharecommunity.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zqy.sharecommunity.entity.Book;
import com.zqy.sharecommunity.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author zqy
 * @Date 2020/04/26
 */
@Controller
@RequestMapping("/admin")
public class AdminBookCtrl {

    @Autowired
    private BookService bookService;


    @GetMapping("/book/all")
    public String getBookPage(Model model, @RequestParam(value = "title",required = false) String title, @RequestParam(defaultValue="1") Integer pageNum,
                              @RequestParam(defaultValue="10") Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Book> book = bookService.findBookBySelective(title);

        PageInfo<Book> pageInfo=new PageInfo<>(book);

        model.addAttribute("books",pageInfo.getList());
        model.addAttribute("pageInfo",pageInfo);

        return "/site/admin/admin-book";
    }
}
