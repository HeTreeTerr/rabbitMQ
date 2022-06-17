package com.hss.tx;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 事务模式：
 * 优：可以保障消息成功到达mq中
 * 缺：多次请求，降低mq的吞吐量
 */
public class TxSend {

    private static final String QUEUE_NAME="test_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        boolean durable=true;//消息队列（queue）是否持久化
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);

        try{
            channel.txSelect();
            /**  MessageProperties.PERSISTENT_TEXT_PLAIN--消息持久化 */
            channel.basicPublish("",QUEUE_NAME,MessageProperties.PERSISTENT_TEXT_PLAIN,"hello tx message1".getBytes());
            channel.basicPublish("",QUEUE_NAME,MessageProperties.PERSISTENT_TEXT_PLAIN,"hello tx message2".getBytes());
            channel.basicPublish("",QUEUE_NAME,MessageProperties.PERSISTENT_TEXT_PLAIN,"hello tx message3".getBytes());
            System.out.println("send message success!");
            if(true){
                throw new RuntimeException("我要抛异常");
            }
            channel.txCommit();
        }catch (Exception E){
            channel.txRollback();
            System.out.println("send message txRollback");
        }

        channel.close();
        connection.close();

    }
}
