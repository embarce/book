package com.embrace.demo.activcemq.customer;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;

@Component
public class TopicConsumerListener {

    //topic模式的消费者
    @JmsListener(destination="${spring.jms.topic-name}", containerFactory="topicListener")
    public void readActiveQueue(ObjectMessage message) {
        System.out.println("topic接受到：" + message);
    }
}
