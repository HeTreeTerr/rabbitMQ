package com.hss.work;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息消费者2
 */
public class Recv2 {

    private static final String QUEUE_NAME="test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //MQ 不再对消费者一次发送多个请求，而是消费者处理完一个消息后（确认后），再从队列中获取一个新的
        channel.basicQos(1);
        //定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[2 recv] msg:"+msg);
                try {
                    //等待二秒
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done");
                    //false只确认签收当前的消息，设置为true的时候则代表签收该消费者所有未签收的消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }

}
