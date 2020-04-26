package com.zqy.sharecommunity.util;

import com.zqy.sharecommunity.entity.Book;
import com.zqy.sharecommunity.entity.DownloadUrl;
import com.zqy.sharecommunity.service.BookService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zqy
 * @Date 2019/12/31
 */
@Component
public class SpiderBookTask {
    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private BookService bookService;

    //@Scheduled(fixedDelay = 100 * 1000)   //每十秒爬一次
    //@Scheduled(cron = "0 0 12 * * ?")   //每天中午12点爬一次
    public void spiderTask() throws Exception {
        //声明需要解析的初始地址
        String url = "http://www.shicimingju.com/book/index.html";

        //获取页面数据
        String html = httpUtils.doGetHtmlUtf8(url);
        //解析数据
        this.parse(html);

        System.out.println("书籍数据抓取完成！");
    }


    //解析页面，获取书籍数据并存储
    private void parse(String html) throws Exception {
        //解析html获取Document
        Document doc = Jsoup.parse(html);

        Elements elements = doc.select("div#main_left").select("div.card").select("div.booknark_card");
        String attr = elements.get(5).select("h2").select("a").attr("href");


        for (Element book : elements) {
            String bookMark = book.select("h2").text();
            if(book.select("h2").select("a").attr("href").equals("")){
                break;
            }
            String bookMarkIntro = book.select("div.des").text();
            Elements bookUrls = book.select("ul").select("li").select("a");
//            String titleUrl = bookMark.select("a").attr("href");
//            String bookMarkHtml = httpUtils.doGetHtmlUtf8("http://www.shicimingju.com" + titleUrl);
//            Document bookMarkDoc = Jsoup.parse(bookMarkHtml);
//            //四大名著 介绍
//            String bookMarkIntro = bookMarkDoc.select("div.des").text();
//            Elements bookUrls = bookMarkDoc.select("div.book-item").select("a");


            for (Element bookUrl : bookUrls) {

                String url = bookUrl.attr("href");
                if(url == null){
                    break;
                }
                //书籍详情
                String bookHtml = httpUtils.doGetHtmlUtf8("http://www.shicimingju.com/" + url);
                Document bookDoc = Jsoup.parse(bookHtml);
                Elements bookDetail = bookDoc.select("div.card").select("div.bookmark-list");
                String bookName = bookDetail.select("h1").text();
                String bookImg = "http://www.shicimingju.com"+bookDetail.select("div").select("img.book-img").attr("src");
                Elements bookItem = bookDetail.select("div").select("p");
                String period = bookItem.get(0).text().substring(bookItem.get(0).text().indexOf("：") + 1);
                String bookAuthor = bookItem.get(1).text().substring(bookItem.get(1).text().indexOf("：") + 1);
                String bookIntro = bookItem.get(2).text();

                Book bookInfo = new Book();
                bookInfo.setBookMark(bookMark);
                bookInfo.setBookMarkIntro(bookMarkIntro);
                bookInfo.setBookUrl(url);
                bookInfo.setBookName(bookName);
                bookInfo.setBookImg(bookImg);
                bookInfo.setPeriod(period);
                bookInfo.setBookAuthor(bookAuthor);
                bookInfo.setBookIntro(bookIntro);

                bookService.save(bookInfo);


                Elements bookMulu = bookDetail.select("div.book-mulu").select("ul").select("li");
                List<DownloadUrl> urls = new ArrayList<>();
                for (Element chapter : bookMulu) {
                    String chapterName = chapter.select("a").text();
                    String chapterUrl = chapter.select("a").attr("href");
                    DownloadUrl downloadUrl = new DownloadUrl();
                    downloadUrl.setChapterName(chapterName);
                    downloadUrl.setDownloadUrl(chapterUrl);
                    downloadUrl.setBookId(bookInfo.getId());
                    urls.add(downloadUrl);
                }
                bookService.saveDownloadurl(urls);
//                    String contentHtml = httpUtils.doGetHtmlUtf8("http://www.shicimingju.com" + chapterUrl);
//                    Document contenDoc = Jsoup.parse(contentHtml);
//                    Elements content = contenDoc.select("div.chapter_content").select("p");
//
//
//                    if(content.size()<=0){
//                        content=contenDoc.select("div.chapter_content");
//                        String[] text = content.text().split(" ");
//                        for(String s:text){
//                            System.out.println();
//                        }
//
//                        System.out.println();
//                    }else {
//                        for (Element text : content) {
//
//                            System.out.println();
//                        }
//                    }
//                    System.out.println();
            }
            System.out.println();
        }
    }
}

