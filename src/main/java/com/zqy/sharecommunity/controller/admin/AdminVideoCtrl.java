package com.zqy.sharecommunity.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zqy.sharecommunity.entity.VideoDTO;
import com.zqy.sharecommunity.service.VideoService;
import com.zqy.sharecommunity.util.CommunityConstant;
import com.zqy.sharecommunity.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zqy
 * @Date 2020/04/26
 */

@Controller
@RequestMapping("/admin")
public class AdminVideoCtrl{

    @Autowired
    private VideoService videoService;

    @GetMapping("/video/all")
    public String getVideoPage(Model model,@RequestParam(value = "title",required = false) String title, @RequestParam(defaultValue="1") Integer pageNum,
                               @RequestParam(defaultValue="10") Integer pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<VideoDTO> videoDTOS = videoService.findVideosSelective(title);
        PageInfo<VideoDTO> pageInfo=new PageInfo<>(videoDTOS);

        model.addAttribute("videos",pageInfo.getList());
        model.addAttribute("pageInfo",pageInfo);


        return "/site/admin/admin-video";
    }

    @GetMapping("/video/update/{id}")
    @ResponseBody
    public void updateStatus(@PathVariable("id") int id){

        videoService.updateStatus(1,id);

//        return CommunityUtil.getJSONString(0,"操作成功");
    }
}
