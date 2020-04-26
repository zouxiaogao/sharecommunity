package com.zqy.sharecommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@MapperScan("com.zqy.sharecommunity.dao")
@EnableScheduling
public class SharecommunityApplication {

    @PostConstruct
    public void init(){
        //解决netty启动冲突问题 see Netty4Utils方法  ： setAvailableProcessors()
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }


    public static void main(String[] args) {
        SpringApplication.run(SharecommunityApplication.class, args);
    }

}
