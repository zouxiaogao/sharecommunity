package com.zqy.sharecommunity.controller;

import com.zqy.sharecommunity.annotation.LoginRuqired;
import com.zqy.sharecommunity.entity.User;
import com.zqy.sharecommunity.service.FollowService;
import com.zqy.sharecommunity.service.LikeService;
import com.zqy.sharecommunity.service.UserService;
import com.zqy.sharecommunity.util.CommunityConstant;
import com.zqy.sharecommunity.util.CommunityUtil;
import com.zqy.sharecommunity.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @Author zqy
 * @Date 2020/03/15
 */
@Controller
@RequestMapping("/user")
public class UserCtrl implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(UserCtrl.class);

    @Value("${sharecommunity.path.upload}")
    private String uploadPath;

    @Value("${sharecommunity.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;


    @LoginRuqired
    @GetMapping("/setting")
    public String getSettingPage() {
        return "/site/setting";
    }


    /**
     * 更新头像
     * **/
    @LoginRuqired
    @PostMapping("/upload")
    public String uploadHeader(MultipartFile headerImage, Model model){
        if(headerImage == null){
            model.addAttribute("error","您还没有选择图片");
            return "/site/setting";
        }

        //上传文件的原始名字
        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if(StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件的格式不正确");
            return "/site/setting";
        }
        //生成随机文件名
        filename= CommunityUtil.generateUUID()+suffix;
        //确定文件的存放路径
        File dest  = new File(uploadPath + "/" + filename);
        try {
            //存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败："+e.getMessage());
            throw  new RuntimeException("上传文件失败，服务器发生异常！",e);
        }

        //更新当前用户的头像路径（web访问路径）
        //http://localhost:8080/share/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl=domain+contextPath+"/user/header/"+filename;
        userService.updateHeader(user.getId(),headerUrl);

        return "redirect:/index";
    }

    @GetMapping("/header/{filename}")
    public void getHeader(@PathVariable("filename") String filename, HttpServletResponse response){
        //服务器存放路径
        filename=uploadPath+"/"+filename;
        //文件后缀
        String suffix = filename.substring(filename.lastIndexOf("."));
        //响应图片
        response.setContentType("image/"+suffix);
        FileInputStream fis=null;
        try {
            OutputStream os = response.getOutputStream();
            fis = new FileInputStream(filename);
            byte[] buffer = new byte[1024];
            int b=0;
            while ((b=fis.read(buffer)) != -1){
                os.write(buffer,0,b);
            }

        } catch (IOException e) {
            logger.error("读取头像失败！"+e.getMessage());
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 修改密码
     *
     */
    @LoginRuqired
    @PostMapping("/updatePassword")
    @ResponseBody
    public Map<String, Object> updatePassword(String oldPassword,String newPassword,Model model){
        Map<String, Object> map = userService.updatePassword(hostHolder.getUser().getId(), oldPassword, newPassword);
        if(map == null && map.isEmpty()){
            map.put("msg","密码修改成功");
            return map;
        }else {
            map.put("passwordMsg",map.get("passwordMsg"));
            return map;
        }
    }


    /**
     * 个人主页
     * **/
    @GetMapping("/profile/{userId}")
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserByUserId(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在!");
        }

        // 用户
        model.addAttribute("user", user);
        // 点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

        // 关注数量
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);
        // 粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount", followerCount);
        // 是否已关注
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);

        return "/site/profile";
    }



}
