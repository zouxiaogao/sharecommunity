package com.zqy.sharecommunity;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author zqy
 * @Date 2020/03/23
 */

/**
 *  *
 *  * cd E:\kafka_2.13-2.4.1
 *
 *  \bin\windows
 *  *
 *  *
 *  * 启动zookeeper ：bin\windows\zookeeper-server-start.bat config\zookeeper.properties
 *  * 启动kafka ： bin\windows\kafka-server-start.bat config\server.properties
 *  *
 *  *
 *  * 创建主题 topic ：test
 *  *  kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
 *  *
 *  *  列举主题：
 *  *  kafka-topics --list --bootstrap-server localhost:9092
 *  *
 *  *  往test主题发送消息
 *  *  创建生产者
 *  *  kafka-console-producer.bat --broker-list  localhost:9092 --topic test   broker（服务器）
 *  *
 *  *  创建消费者读取主题中的消息
 *  *  kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning  (从头开始读)
 *  *
 * **/


@SpringBootTest
public class KafkaTests {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void testkafka(){

        kafkaProducer.sendMessage("test","你好");
        kafkaProducer.sendMessage("test","在吗");
        kafkaProducer.sendMessage("test","kkp");

        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

//生产者
@Component
class KafkaProducer{

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(String topic,String content){
        kafkaTemplate.send(topic,content);
    }
}


//消费者
@Component
class KafkaConsumer{

    @KafkaListener(topics = {"test"})
    public void handlerMessage(ConsumerRecord record){
        System.out.println(record.value());
    }
}