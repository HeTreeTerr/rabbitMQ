package com.hss.simple;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者获取消息
 */
public class Recv {

    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //3.x版本api，不推荐使用
        //oldapi();

        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //创建频道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        DefaultConsumer consumer = new DefaultConsumer(channel){
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[recv] msg:"+msg);
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);

    }

    private static void oldapi() throws IOException, TimeoutException, InterruptedException {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //创建频道
        Channel channel = connection.createChannel();
        //定义队列的消费者（已过时）
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            String msgString = new String(delivery.getBody());
            System.out.println("[recv] msg:"+msgString);
        }
    }
}
