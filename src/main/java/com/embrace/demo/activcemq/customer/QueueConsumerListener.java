package com.embrace.demo.activcemq.customer;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;
@Mapper
@Component
public class QueueConsumerListener {

    //queue模式的消费者
    @JmsListener(destination="${spring.jms.queue-name}", containerFactory="queueListener")
    public void readActiveQueue(ObjectMessage message) {
        System.out.println("queue接受到：" + message.toString());
    }
}
