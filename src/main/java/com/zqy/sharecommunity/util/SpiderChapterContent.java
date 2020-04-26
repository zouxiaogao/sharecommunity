package com.zqy.sharecommunity.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author zqy
 * @Date 2020/04/04
 */
@Component
public class SpiderChapterContent {

    @Autowired
    private HttpUtils httpUtils;


    public String getBookContent(String url){
        String html = httpUtils.doGetHtmlUtf8("http://www.shicimingju.com/" + url);
        Document doc = Jsoup.parse(html);
        Elements select = doc.select("div#main_left").select("div.chapter_content");
        String content = select.toString();
        System.out.println();
        return content;
    }


}
