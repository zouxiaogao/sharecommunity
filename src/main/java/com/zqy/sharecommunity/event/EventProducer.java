package com.zqy.sharecommunity.event;

import com.alibaba.fastjson.JSONObject;
import com.zqy.sharecommunity.entity.Event;
import com.zqy.sharecommunity.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author zqy
 * @Date 2020/03/23
 */

/**
 * 事件生产者
 *
 * ***/


@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    //处理事件
    public void fireEvent(Event event){
        //将事件发布到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
