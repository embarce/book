package com.embrace.demo.activcemq.producer;

import com.embrace.demo.cate.entity.CateInfo;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

@RestController
@RequestMapping("/mq")
public class ProducerController {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Topic topic;
    @Autowired Queue queue;

    @PostMapping("/queue/test")
    public String sendQueue(CateInfo str) {
        this.sendMessage(this.queue, str);
        return "success";
    }

    @PostMapping("/topic/test")
    public String sendTopic(CateInfo str) {
        this.sendMessage(this.topic, str);
        return "success";
    }

    // 发送消息，destination是发送到的队列，message是待发送的消息
    private void sendMessage(Destination destination, CateInfo message){
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }
}