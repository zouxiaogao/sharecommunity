package com.zqy.sharecommunity.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zqy.sharecommunity.entity.Music;
import com.zqy.sharecommunity.service.MusicService;
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
public class AdminMusicCtrl {

    @Autowired
    private MusicService musicService;

    @GetMapping("/music/all")
    public String getMusicPage(Model model, @RequestParam(value = "title",required = false) String title, @RequestParam(defaultValue="1") Integer pageNum,
                               @RequestParam(defaultValue="10") Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Music> music = musicService.findMusicBySelective(title);
        PageInfo<Music> pageInfo=new PageInfo<>(music);

        model.addAttribute("music",pageInfo.getList());
        model.addAttribute("pageInfo",pageInfo);

        return "/site/admin/admin-music";
    }
}
