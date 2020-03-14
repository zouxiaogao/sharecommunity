package com.zqy.sharecommunity.util;

import com.zqy.sharecommunity.entity.Video;
import com.zqy.sharecommunity.entity.VideoAttr;
import com.zqy.sharecommunity.entity.DownloadUrl;
import com.zqy.sharecommunity.service.VideoService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zqy
 * @Date 2019/12/24
 */

@Component
public class SpiderVideoTask {
    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private VideoService videoService;

    //@Scheduled(fixedDelay = 100 * 1000)   //每十秒爬一次
    //@Scheduled(cron = "0 0 12 * * ?")   //每天中午12点爬一次
    public void spiderTask() throws Exception {
        //声明需要解析的初始地址
        String url = "http://www.kuyunzy1.com";

        //获取页面数据
        String html = httpUtils.doGetHtml(url);
        //解析数据
        this.parse(html);

        System.out.println("电影数据抓取完成！");
    }


    //解析页面，获取电影数据并存储
    private void parse(String html) throws Exception {

        //解析html获取Document
        Document doc = Jsoup.parse(html);

        Elements rows = doc.getElementsByClass("row");
        for(Element row:rows){
            Elements tds = row.select("td");

            //搜索的影片信息
            Video video=new Video();

            video.setVideoNewName(tds.get(0).text());
            String area = tds.get(1).text();
            if("大陆地区".equals(area)){
                video.setAreaTypeId(1);
            }else if ("台湾地区".equals(area)){
                video.setAreaTypeId(2);
            }else if ("日本地区".equals(area)){
                video.setAreaTypeId(3);
            }else if ("韩国地区".equals(area)){
                video.setAreaTypeId(4);
            }else if ("欧美地区".equals(area)){
                video.setAreaTypeId(5);
            }else if ("香港地区".equals(area)){
                video.setAreaTypeId(5);
            }else if ("其他地区".equals(area)){
                video.setAreaTypeId(7);
            }

            String type = tds.get(2).text();
            if("动作片".equals(type)){
                video.setVideoTypeId(1);
            }else if ("喜剧片".equals(type)){
                video.setVideoTypeId(2);
            }else if ("爱情片".equals(type)){
                video.setVideoTypeId(3);
            }else if ("科幻片".equals(type)){
                video.setVideoTypeId(4);
            }else if ("恐怖片".equals(type)){
                video.setVideoTypeId(5);
            }else if ("剧情片".equals(type)){
                video.setVideoTypeId(6);
            }else if ("战争片".equals(type)){
                video.setVideoTypeId(7);
            }else if ("动画片".equals(type)){
                video.setVideoTypeId(8);
            }else if ("微电影".equals(type)){
                video.setVideoTypeId(9);
            }else if ("记录片".equals(type)){
                video.setVideoTypeId(10);
            }else if ("动漫剧场".equals(type)){
                video.setVideoTypeId(11);
            }else if ("欧美剧".equals(type)){
                video.setVideoTypeId(12);
            }else if ("综艺节目".equals(type)){
                video.setVideoTypeId(13);
            }else if ("国产剧".equals(type)){
                video.setVideoTypeId(14);
            }else if ("海外剧".equals(type)){
                video.setVideoTypeId(16);
            }else if ("日本剧".equals(type)){
                video.setVideoTypeId(17);
            }else if ("韩国剧".equals(type)){
                video.setVideoTypeId(18);
            }else if ("台湾剧".equals(type)){
                video.setVideoTypeId(19);
            }else if ("香港剧".equals(type)){
                video.setVideoTypeId(20);
            }

            //更新时间
            video.setUpdateTime(StringToDateFormat.StringToDate(tds.get(3).text()));
            video.setVideoUrl("http://www.kuyunzy1.com" + tds.get(0).selectFirst("a").attr("href"));



            videoService.saveVideo(video);

            VideoAttr videoAttr=new VideoAttr();
            //获取影片的详情
            String detail = httpUtils.doGetHtml("http://www.kuyunzy1.com" + tds.get(0).selectFirst("a").attr("href"));
            Document document = Jsoup.parse(detail);
            Elements trs = document.select("table").get(1).select("tbody");
            //1.图片地址
            String imgUrl = trs.get(0).select("img").attr("src");
            videoAttr.setImg(imgUrl);

            //2.影片信息
            Elements info = trs.get(1).select("tr");

            videoAttr.setVideoName(info.get(0).select("font").text());
            videoAttr.setRemark(info.get(1).select("font").text()); //集数
            videoAttr.setVideoActors(info.get(2).select("font").text());
            videoAttr.setVideoDirector(info.get(3).select("font").text());
            videoAttr.setUpdateTime(StringToDateFormat.StringToDateFomat(info.get(6).select("font").text())); //时间
            videoAttr.setVideoStatus(info.get(7).select("font").text());
            videoAttr.setVideoLanguage(info.get(8).select("font").text());
            videoAttr.setShowDate(info.get(9).select("font").text());
            videoAttr.setIntro(info.get(10).select("font").text());
            videoAttr.setVideoId(video.getId());



            videoService.saveVideoAttr(videoAttr);

            //下载链接
            Elements trs2 = trs.get(4).select("tr");
            List<DownloadUrl> urls = new ArrayList<>();

            for (Element tr :trs2) {
                DownloadUrl url=new DownloadUrl();
                url.setDownloadUrl(tr.select("td").text());
                url.setVideoId(videoAttr.getVideoId());
                urls.add(url);
            }

            videoService.saveDownloadUrl(urls);
            System.out.println();
        }

    }

}
