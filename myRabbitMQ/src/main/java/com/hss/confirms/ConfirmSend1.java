package com.hss.confirms;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * confirm
 * 普通 发一条 waitForConfirms()
 */
public class ConfirmSend1 {

    private static final String QUEUE_NAME="test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        boolean durable=true;//消息队列（queue）是否持久化
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        //生产者调用confirmSelect 将channel设置为confire模式
        channel.confirmSelect();

        String msgString = "hello confirm message";

        /**  MessageProperties.PERSISTENT_TEXT_PLAIN--消息持久化 */
        channel.basicPublish("",QUEUE_NAME,MessageProperties.PERSISTENT_TEXT_PLAIN,msgString.getBytes());

        if(!channel.waitForConfirms()){
            System.out.println("message send faild");
        }else{
            System.out.println("message send ok");
        }
        channel.close();
        connection.close();

    }
}
