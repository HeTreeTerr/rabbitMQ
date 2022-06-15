package com.hss.simple;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发送一个简单消息
 * 消息生产者
 */
public class Send {

    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        /*
        创建队列,声明并创建一个队列，如果队列已存在，则使用这个队列
        第一个参数：队列名称ID
        第二个参数：是否持久化，false对应不持久化数据，MQ停掉数据就会丢失
        第三个参数：是否队列私有化，false则代表所有消费者都可以访问，true代表只有第一次拥有它的消费者才能一直使用，其他消费者不让访问
        第四个：是否自动删除,false代表连接停掉后不自动删除掉这个队列
        其他额外的参数, null
        */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello simple (你好)!";

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("--send msg:"+msg);

        channel.close();
        connection.close();
    }
}
