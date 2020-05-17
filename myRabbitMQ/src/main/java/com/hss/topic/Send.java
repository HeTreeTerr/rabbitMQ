package com.hss.topic;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式（使用交换机实现多个消费者共同消费）
 * topic（支持匹配符，其余和路由模式相同）
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        //声明交换机
        /**
         * fanout 无论routingKey是否一致或是为空，都会发送给所有队列
         * direct 处理路由键，只发送给绑定时routingkey一致的队列
         * topic（支持匹配符，其余和路由模式相同）
         */
        channel.exchangeDeclare(EXCHANGE_NAME,"topic",true,false,null);//分发

        String routingkey = "goods.delete";

        //发送消息
        String msg = "商品...";
        channel.basicPublish(EXCHANGE_NAME,routingkey,null,msg.getBytes());

        System.out.println("send:"+msg);


        channel.close();
        connection.close();

    }
}
