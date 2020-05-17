package com.hss.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitExchangeConfig {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    public RabbitExchangeConfig() {
    }

    /**
     * fanout 无论routingKey是否一致或是为空，都会发送给所有队列
     * direct 处理路由键，只发送给绑定时routingkey一致的队列
     * topic（支持匹配符，其余和路由模式相同）
     */
    @Bean
    FanoutExchange contractFanoutExchange() {
        FanoutExchange fanoutExchange = new FanoutExchange("com.exchange.fanout");
        this.rabbitAdmin.declareExchange(fanoutExchange);
        return fanoutExchange;
    }

    @Bean
    TopicExchange contractTopicExchangeDurable() {
        TopicExchange contractTopicExchange = new TopicExchange("com.exchange.topic");
        this.rabbitAdmin.declareExchange(contractTopicExchange);
        return contractTopicExchange;
    }

    @Bean
    DirectExchange contractDirectExchange() {
        DirectExchange contractDirectExchange = new DirectExchange("com.exchange.direct");
        this.rabbitAdmin.declareExchange(contractDirectExchange);
        return contractDirectExchange;
    }

    @Bean
    Queue queueHello() {
        /**
         * 创建队列
         * param1 队列名称
         * param2 队列持久化
         * param3 独占队列
         * param4 自动删除消息（消息持久化）
         */
        Queue queue = new Queue("com.queue.notify.hello", true,false,false);
        this.rabbitAdmin.declareQueue(queue);
        //添加绑定关系
        this.rabbitAdmin.declareBinding(new Binding("com.queue.notify.hello",Binding.DestinationType.QUEUE,"com.exchange.topic","topic",null));
        //解除绑定关系
        this.rabbitAdmin.removeBinding(new Binding("com.queue.notify.hello",Binding.DestinationType.QUEUE,"com.exchange.topic","topic",null));

        return queue;
    }

}
