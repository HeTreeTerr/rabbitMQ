package com.hss.tx;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息消费者
 */
public class TxRecv {

    private static final String QUEUE_NAME="test_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        //声明队列
        boolean durable=true;//消息队列（queue）是否持久化
        channel.queueDeclare("QUEUE_NAME",durable,false,false,null);

        //定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("recv[tx ] msg:"+msg);
            }
        };

        //监听队列
        boolean autoAck=true;//自动应答
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
