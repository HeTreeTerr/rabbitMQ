package com.hss.confirms;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeoutException;

public class ConfirmRecv {

    private static final String QUEUE_NAME="test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1);

        byteBuffer=null;
        //声明队列
        boolean durable=true;//消息队列（queue）是否持久化
        channel.queueDeclare("QUEUE_NAME",durable,false,false,null);

        //定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("recv[confirm ] msg:"+msg);
            }
        };

        //监听队列
        boolean autoAck=true;//自动应答
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
