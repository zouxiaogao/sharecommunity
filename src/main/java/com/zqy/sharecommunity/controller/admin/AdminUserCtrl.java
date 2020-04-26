package com.zqy.sharecommunity.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zqy.sharecommunity.entity.User;
import com.zqy.sharecommunity.service.UserService;
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
public class AdminUserCtrl {

    @Autowired
    private UserService userService;

    @GetMapping("/user/all")
    public String getUserPage(Model model, @RequestParam(value = "title",required = false) String title, @RequestParam(defaultValue="1") Integer pageNum,
                              @RequestParam(defaultValue="10") Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<User> users = userService.findUsersBySelective(title);
        PageInfo<User> pageInfo=new PageInfo<>(users);

        model.addAttribute("users",pageInfo.getList());
        model.addAttribute("pageInfo",pageInfo);

        return "/site/admin/admin-user";
    }

    @GetMapping("/unActive/all")
    public String getUnActiveUserPage(Model model){
        return "/site/admin/admin-unactive-user";
    }
}
