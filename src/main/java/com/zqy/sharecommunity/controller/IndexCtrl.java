package com.zqy.sharecommunity.controller;

import com.zqy.sharecommunity.entity.DiscussPost;
import com.zqy.sharecommunity.entity.User;
import com.zqy.sharecommunity.service.DiscussPostService;
import com.zqy.sharecommunity.service.UserService;
import com.zqy.sharecommunity.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zqy
 * @Date 2019/12/02
 */
@Controller
public class IndexCtrl {

    @Autowired
    private DiscussPostService discussService;
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String getIndexPage(Model model, Page page){
        //方法调用前，SpringMvc会自动实例化Model和Page对象，并Page对自动注入Model
        //所以，在Thymeleaf中可以调用page的属性。

        //设置总页数
        page.setRows(discussService.selectDiscussPostRows(0));
        //设置页面链接
        page.setPath("/index");
        List<DiscussPost> list = discussService.selectDiscussPosts(0, page.getOffset(), page.getLimit());
        ArrayList<Map<String,Object>> discussPosts=new ArrayList<>();
        if(list!=null){
            for(DiscussPost post:list){
                Map<String ,Object> map=new HashMap<>();
                map.put("post",post);
                User user = userService.findUserByUserId(post.getUserId());
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }


}
