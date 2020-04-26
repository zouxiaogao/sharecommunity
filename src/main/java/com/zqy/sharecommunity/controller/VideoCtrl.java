package com.zqy.sharecommunity.controller;

import com.zqy.sharecommunity.entity.DownloadUrl;
import com.zqy.sharecommunity.entity.VideoAttr;
import com.zqy.sharecommunity.entity.VideoDTO;
import com.zqy.sharecommunity.service.VideoService;
import com.zqy.sharecommunity.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author zqy
 * @Date 2020/03/30
 */
@Controller
@RequestMapping("/video")
public class VideoCtrl {

    @Autowired
    private VideoService videoService;


    @GetMapping("/all")
    public String getVideoPage(Model model, Page page){

        page.setLimit(20);
        page.setPath("/video/all");
        page.setRows(videoService.findVideoCount());
        List<VideoDTO> videos = videoService.findVideos(page.getOffset(), page.getLimit());

        model.addAttribute("videos",videos);

        return "/site/video";

    }




    @GetMapping("/detail/{videoId}")
    public String getVideoInfoPage(@PathVariable("videoId")int videoId, Model model){
        VideoDTO video = videoService.findVideoById(videoId);

        model.addAttribute("v",video);
        VideoAttr videoAttr = videoService.findVideoAttr(videoId);
        model.addAttribute("video",videoAttr);

        List<DownloadUrl> downloadUrl = videoService.findDownloadUrl(videoId);
        for (DownloadUrl url:downloadUrl){
            if(url.getDownloadUrl().equals("全选")){
                url.setDownloadUrl("");
            }
        }
        model.addAttribute("urls",downloadUrl);



        return "/site/video-attr";
    }


}
