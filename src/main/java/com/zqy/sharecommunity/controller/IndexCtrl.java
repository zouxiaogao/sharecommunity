package com.zqy.sharecommunity.controller;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author zqy
 * @Date 2019/12/02
 */
public class IndexCtrl {

    @GetMapping("/share")
    public String index(){
        return "share resource!";
    }
}
