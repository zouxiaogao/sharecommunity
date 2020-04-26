package com.zqy.sharecommunity.controller;

import com.zqy.sharecommunity.entity.Music;
import com.zqy.sharecommunity.entity.VideoDTO;
import com.zqy.sharecommunity.service.MusicService;
import com.zqy.sharecommunity.util.DownloadFile;
import com.zqy.sharecommunity.util.Page;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author zqy
 * @Date 2020/03/31
 */

@Controller
@RequestMapping("/music")
public class MusicCtrl {

    private static final Logger logger = LoggerFactory.getLogger(UserCtrl.class);
    @Value("${sharecommunity.path.upload}")
    private String uploadPath;

    @Value("${sharecommunity.path.domain}")
    private String domain;
    @Autowired
    private MusicService musicService;

    @GetMapping("/all")
    public String getMusicPage(Model model, Page page){
        page.setLimit(20);
        page.setPath("/music/all");
        page.setRows(musicService.findMusicCount());
        List<Music> music = musicService.findMusic(page.getOffset(), page.getLimit());

        model.addAttribute("music",music);
        return "/site/music";

    }

//    @GetMapping("/download/{id}")
//    public void download(@PathVariable("id") int id){
//
//
//        Music music = musicService.findMusicById(id);
//
//
//        DownloadFile.downloadFile(music.getDownloadUrl(),uploadPath);
//
//        System.out.println("下载完成");
//    }

    @GetMapping("/download/{musicId}")
    public String downloadAttachment(@PathVariable("musicId") int musicId,HttpServletRequest request, HttpServletResponse response,Model model) {
        Music music=musicService.findMusicById(musicId);
        String downLoadPath=music.getDownloadUrl();
        String fileName=music.getTitle();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            String os = System.getProperty("os.name");
            if(os.toLowerCase().indexOf("windows") != -1){
                fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            }else{
                //判断浏览器
                String userAgent = request.getHeader("User-Agent").toLowerCase();
                if(userAgent.indexOf("msie") > 0){
                    fileName = URLEncoder.encode(fileName, "ISO-8859-1");
                }
            }
            //响应二进制流
            response.setContentType("application/octet-stream");
            response.reset();//清除response中的缓存
            //根据网络文件地址创建URL
            URL url = new URL(downLoadPath);
            //获取此路径的连接
            URLConnection conn = url.openConnection();
            Long fileLength = conn.getContentLengthLong();//获取文件大小
            if (fileLength <=0){
                model.addAttribute("msg","该歌曲为收费曲目，暂时无法下载,选择其他歌曲试试吧");
                return "/site/download";
            }
            //设置reponse响应头，真实文件名重命名，就是在这里设置，设置编码
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + fileName+".mp3");
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(conn.getInputStream());//构造读取流
            bos = new BufferedOutputStream(response.getOutputStream());//构造输出流
            byte[] buff = new byte[1024];
            int bytesRead;
            //每次读取缓存大小的流，写到输出流
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            response.flushBuffer();//将所有的读取的流返回给客户端
        } catch (IOException e) {
            logger.error("文件下载失败！"+e.getMessage());
        }finally{
            try{
                if(null != bis){
                    bis.close();
                }
                if(null != bos){
                    bos.close();
                }
            }catch(IOException e){
                logger.error("文件下载失败！"+e.getMessage());
            }
        }

        return null;
    }
}
