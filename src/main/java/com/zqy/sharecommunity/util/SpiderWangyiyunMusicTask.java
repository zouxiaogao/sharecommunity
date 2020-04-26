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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author zqy
 * @Date 2019/12/27
 */
@Component
public class SpiderWangyiyunMusicTask {

    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private MusicService musicService;


    //@Scheduled(fixedDelay = 100 * 1000)   //每十秒爬一次
    //@Scheduled(cron = "0 0 12 * * ?")   //每天中午12点爬一次
    public void spiderTask() throws Exception {

        //声明需要解析的初始地址
        String url = "https://music.163.com/discover/toplist";

        //获取页面数据
        String html = httpUtils.doGetHtmlUtf8(url);
        //解析数据
        this.parse(html);

        System.out.println("网易云音乐数据抓取完成！");
    }


    private void parse(String html) throws Exception {
        //解析html获取Document
        Document doc = Jsoup.parse(html);

        Elements elements = doc.select("div.g-wrap12").select("div#song-list-pre-cache").select("ul").select("li");

        for (Element song:elements){
            //获取songId
            //id=1411558182
            String songId = song.select("a").attr("href").substring(9);
            //获取音乐详情
            String songDetail = httpUtils.doGetHtmlUtf8("https://music.163.com/song?id=" + songId);
            Document parseSong = Jsoup.parse(songDetail);
            Elements img = parseSong.select("div.f-cb").select("img");
            //专辑大图图片
            String album_huge = img.attr("data-src");
            //专辑小图
            String album_small = img.attr("src");
            //获取音乐信息
            Elements songInfo = parseSong.select("div.f-cb").select("div.cnt");
            //歌名
            String songName = songInfo.select("div.hd").select("div.tit").select("em").text();
            //歌手 专辑名称
            Elements info = songInfo.select("p");
            String author = info.get(0).select("a").text();
            String album_name = info.get(1).select("a").text();
            String album_id = info.get(1).select("a").attr("href").substring(10);
            String songLyric = httpUtils.doGetHtmlUtf8(("http://music.163.com/api/song/lyric?id=" + songId + "&lv=1&kv=1&tv=-1"));
            JSONObject json = (JSONObject) JSON.parse(songLyric);
            JSONObject lrc = (JSONObject) JSON.parse(json.getString("lrc"));
            String lyric="";
            if (lrc!=null){
                //歌词
                lyric =lrc.getString("lyric");
            }

            //获取下载链接
            String downloadUrl = "http://music.163.com/song/media/outer/url?id="+songId;


            Music music=new Music();
            music.setTitle(songName);
            music.setAlbumTitle(album_name);
            music.setAuthor(author);
            music.setPicHuge(album_huge);
            music.setPicSmall(album_small);
            music.setDownloadUrl(downloadUrl);
            if(lyric != null){
                music.setLyric(lyric);
            }

            musicService.save(music);
        }

        System.out.println();

    }

}
