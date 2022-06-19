package com.hss.rabbitmq.listener.basic;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class TopicListenerWell implements MessageListener {
    @Override
    public void onMessage(Message message) {
        //打印消息
        System.out.println(this.getClass().getName() + "--" + new String(message.getBody()));
    }
}
