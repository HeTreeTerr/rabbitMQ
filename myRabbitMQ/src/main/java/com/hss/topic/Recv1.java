package com.hss.topic;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv1 {

    private static final String QUEUE_NAME = "test_queue_topic_1";

    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        //声明连接
        Connection connection = ConnectionUtils.getConnection();
        //声明通道
        final Channel channel = connection.createChannel();
        //声明队列 queueDeclare（名字，是否知持久化道，独占的queue， 不使用时是否自内动删除，其他参数）；
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        String routingkey = "goods.add";
        //绑定队列到交换机（转发器）
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,routingkey);
        String routingkey1 = "goods.update";
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,routingkey1);

        //保证一次分发一个
        channel.basicQos(1);
        //定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[1 recv] msg:"+msg);
                try {
                    //等待一秒
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done");
                    //手动回执一个消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //监听队列
        boolean autoAck=false;//自动应答false
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
