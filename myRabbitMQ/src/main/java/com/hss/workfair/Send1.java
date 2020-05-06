package com.hss.workfair;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 一对多queue一对多测试
 * 公平分发fair dipatch
 * (能者多劳)
 */
public class Send1 {

    private static final String QUEUE_NAME="test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //声明队列
        boolean durable=true;//消息队列（queue）是否持久化
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);

        /**
         * 每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
         * 限制发送个同一个消费者不得超过一条消息
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        for(int i = 0; i<50; i++){
            String msg = "hello"+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(i*20);
            System.out.println("--send msg:"+msg);
        }

        channel.close();
        connection.close();
    }
}
