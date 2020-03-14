package com.zqy.sharecommunity.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zqy.sharecommunity.entity.Music;
import com.zqy.sharecommunity.service.MusicService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author zqy
 * @Date 2019/12/26
 */
@Component
public class SpiderBaiduMusicTask {

    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private MusicService musicService;


    //@Scheduled(fixedDelay = 100 * 1000)   //每十秒爬一次
    //@Scheduled(cron = "0 0 12 * * ?")   //每天中午12点爬一次
    public void spiderTask() throws Exception {

        //声明需要解析的初始地址
        String url = "http://music.taihe.com/top/dayhot";

        //获取页面数据
        String html = httpUtils.doGetHtmlUtf8(url);
        //解析数据
        this.parse(html);

        System.out.println("百度音乐数据抓取完成！");
    }


    //解析页面，获取电影数据并存储
    private void parse(String html) throws Exception {

        //解析html获取Document
        Document doc = Jsoup.parse(html);

        Elements elements = doc.select("div#songListWrapper").select("ul").select("li")
                .select("div.song-item").select("span.song-title");

        for (Element song:elements){

            //获取songId
            String songId = song.select("a").attr("href").substring(6);

            //获取音乐详情
            String songDetail = httpUtils.doGetHtmlUtf8("http://musicapi.taihe.com/v1/restserver/ting?method=baidu.ting.song.playAAC&songid=" + songId);

            JSONObject json = (JSONObject) JSON.parse(songDetail);
            JSONObject bitrate = (JSONObject) JSON.parse(json.getString("bitrate"));
            JSONObject songinfo = (JSONObject) JSON.parse(json.getString("songinfo"));

            Music music=new Music();
            music.setDownloadUrl(bitrate.getString("file_link"));
            music.setTitle(songinfo.getString("title"));
            music.setAuthor(songinfo.getString("author"));
            music.setCountry(songinfo.getString("country"));
            music.setSongwriter(songinfo.getString("songwriting"));
            music.setLanguage(songinfo.getString("language"));
            music.setCompose(songinfo.getString("compose"));
            music.setAlbumTitle(songinfo.getString("album_title"));
            music.setPicHuge(songinfo.getString("pic_huge"));
            music.setPicSmall(songinfo.getString("album_1000_1000"));
            music.setPublishTime(songinfo.getString("publishtime"));
            music.setProxyComoany(songinfo.getString("si_proxycompany"));


//            //获取下载链接
//            String downloadUrl = bitrate.getString("file_link");
//            //获取音乐信息
//            //歌名
//            String title = songinfo.getString("title");
//            //作者
//            String author = songinfo.getString("author");
//            //地区
//            String country = songinfo.getString("country");
//            //词曲
//            String songwriting = songinfo.getString("songwriting");
//            //语言
//            String language = songinfo.getString("language");
//            //创作者
//            String compose = songinfo.getString("compose");
//            //专辑名称
//            String album_title = songinfo.getString("album_title");
//            //专辑大图图片
//            String pic_huge = songinfo.getString("pic_huge");
//            //专辑小图
//            String pic_small = songinfo.getString("album_1000_1000");
//            //发行时间
//            String publishtime = songinfo.getString("publishtime");
//            //发行公司
//            String proxycompany = songinfo.getString("si_proxycompany");


            //获取歌词
//            String songLyric = doGetHtml("http://music.taihe.com/song/" + songId);
            String lrclink = songinfo.getString("lrclink");
            String lyric = httpUtils.doGetHtmlUtf8(lrclink);
            music.setLyric(lyric);

            musicService.save(music);

        }
    }

}
