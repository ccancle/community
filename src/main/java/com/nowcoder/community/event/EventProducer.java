package com.nowcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: community
 * @description: 生产者类
 * @author: Macchac
 * @create: 2020-07-16 17:33
 **/
@Component
public class EventProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    /**处理事件 发送消息*/
    public void fireEvent(Event event){
        //将事件发布到指定的主题上
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));

    }
}
