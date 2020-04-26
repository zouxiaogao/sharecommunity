package com.zqy.sharecommunity;

import com.zqy.sharecommunity.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author zqy
 * @Date 2020/03/16
 */

@SpringBootTest
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;


    @Test
    public void testSensitiveFilter(){
        String text="这里可以◆吸毒◆,嫖◆娼◆,开◆票,妈◆逼,操◆你◆妈,哈哈哈";
        text=sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
