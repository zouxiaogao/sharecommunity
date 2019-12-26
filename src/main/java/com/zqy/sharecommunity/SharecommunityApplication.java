package com.zqy.sharecommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zqy.sharecommunity.dao")
@EnableScheduling
public class SharecommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharecommunityApplication.class, args);
    }

}
