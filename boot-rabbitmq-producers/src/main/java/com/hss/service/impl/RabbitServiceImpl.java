package com.hss.service.impl;

import com.hss.service.RabbitService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitServiceImpl implements RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendPtoPMessage(String message) {
        rabbitTemplate.convertAndSend("com.queue.notify.hello",message);
    }

    @Override
    public void sendFanoutMessage(String message) {
        rabbitTemplate.convertAndSend("com.exchange.fanout","",message);
    }

    @Override
    public void sendTopicMessage(String message) {
        rabbitTemplate.convertAndSend("com.exchange.topic","topic.hss",message);
    }
}
