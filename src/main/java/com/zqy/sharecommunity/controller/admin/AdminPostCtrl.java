package com.zqy.sharecommunity.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zqy.sharecommunity.entity.DiscussPost;
import com.zqy.sharecommunity.entity.User;
import com.zqy.sharecommunity.service.DiscussPostService;
import com.zqy.sharecommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zqy
 * @Date 2020/04/26
 */
@Controller
@RequestMapping("/admin")
public class AdminPostCtrl {

    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @GetMapping("/post/all")
    public String getPostPage(Model model,@RequestParam(value = "title",required = false)String title, @RequestParam(defaultValue="1") Integer pageNum,
                              @RequestParam(defaultValue="10") Integer pageSize){

        PageHelper.startPage(pageNum,pageSize);

        List<DiscussPost> list = discussPostService.findPosts(title);
        PageInfo<DiscussPost> pageInfo=new PageInfo<>(list);
        ArrayList<Map<String,Object>> discussPosts=new ArrayList<>();
        if(list!=null){
            for(DiscussPost post:pageInfo.getList()){
                Map<String ,Object> map=new HashMap<>();
                map.put("post",post);
                User user = userService.findUserByUserId(post.getUserId());
                map.put("user",user);
                discussPosts.add(map);
            }
        }

        model.addAttribute("posts",discussPosts);
        model.addAttribute("pageInfo",pageInfo);


        return "/site/admin/admin-post";
    }


    @GetMapping("/closePost/all")
    public String getClosePostPage(Model model){
        return "/site/admin/admin-close-post";
    }
}
