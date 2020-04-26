package com.zqy.sharecommunity.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author zqy
 * @Date 2020/04/26
 */
@Controller
@RequestMapping("/admin")
public class AdminInexCtrl {

    @GetMapping("/login")
    public String getLoginPage(Model model){
        return "/site/admin/admin-login";
    }


    @GetMapping("/index")
    public String getIndexPage(Model model){

        return "/site/admin/index";

    }

}
