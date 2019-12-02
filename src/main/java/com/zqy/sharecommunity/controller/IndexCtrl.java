package com.zqy.sharecommunity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zqy
 * @Date 2019/12/02
 */
@RestController
@RequestMapping("/share")
public class IndexCtrl {

    @GetMapping("/index")
    public String index(){
        return "share resource!";
    }
}
