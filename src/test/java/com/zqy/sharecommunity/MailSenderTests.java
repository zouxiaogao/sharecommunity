package com.zqy.sharecommunity;

import com.zqy.sharecommunity.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @Author zqy
 * @Date 2020/03/14
 */

@SpringBootTest
public class MailSenderTests {


    @Autowired
    private MailClient mailClient;

    //引入thymeleaf模板引擎
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTestMail(){
        mailClient.sendMail("721795751@qq.com","TEST","Welcome.");
    }

    @Test
    public void testHtmlMail(){
        Context context=new Context();
        context.setVariable("username","sunday");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);
        mailClient.sendMail("721795751@qq.com","HTML",content);
    }
}
