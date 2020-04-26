package com.zqy.sharecommunity.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zqy.sharecommunity.entity.DiscussPost;
import com.zqy.sharecommunity.entity.User;
import com.zqy.sharecommunity.service.DiscussPostService;
import com.zqy.sharecommunity.service.LikeService;
import com.zqy.sharecommunity.service.UserService;
import com.zqy.sharecommunity.util.CommunityConstant;
import com.zqy.sharecommunity.util.CommunityUtil;
import com.zqy.sharecommunity.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zqy
 * @Date 2019/12/02
 */
@Controller
public class IndexCtrl implements CommunityConstant {

    @Autowired
    private DiscussPostService discussService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;

    @GetMapping("/index")
    public String getIndexPage(Model model, @RequestParam(defaultValue="1") Integer pageNum,
                               @RequestParam(defaultValue="10") Integer pageSize){

        PageHelper.startPage(pageNum,pageSize);

        List<DiscussPost> list = discussService.selectDiscussPosts(0);
        PageInfo<DiscussPost> pageInfo=new PageInfo<>(list);
        ArrayList<Map<String,Object>> discussPosts=new ArrayList<>();
        if(list!=null){
            for(DiscussPost post:pageInfo.getList()){
                Map<String ,Object> map=new HashMap<>();
                map.put("post",post);
                User user = userService.findUserByUserId(post.getUserId());
                map.put("user",user);

                //点赞数量
                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
                map.put("likeCount",likeCount);
                discussPosts.add(map);
            }
        }

        model.addAttribute("discussPosts",discussPosts);
        model.addAttribute("pageInfo",pageInfo);
        return "/index";
    }

    @GetMapping("/error")
    public String getErrorPage(){
        return "/error/500";
    }
}
