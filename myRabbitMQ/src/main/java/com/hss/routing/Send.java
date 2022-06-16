package com.hss.routing;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式（使用交换机实现多个消费者共同消费）
 * Direct(处理路由键)
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        //声明交换机
        /**
         * fanout 无论routingKey是否一致或是为空，都会发送给所有队列
         * direct 处理路由键，只发送给绑定时routingkey一致的队列
         */
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);//分发

        Map dataMap = new LinkedHashMap<String, String>();
        dataMap.put("email","邮件：message");
        dataMap.put("sms","短信：message");
        dataMap.put("weChat","微信：message");

        Iterator<Map.Entry<String, String>> itr = dataMap.entrySet().iterator();
        while (itr.hasNext()) {
            //发送消息
            Thread.sleep(200);

            Map.Entry<String, String> me = itr.next();
            channel.basicPublish(EXCHANGE_NAME,me.getKey(),null,me.getValue().getBytes());

            System.out.println("send:"+me.getValue());
        }

        channel.close();
        connection.close();

    }
}
