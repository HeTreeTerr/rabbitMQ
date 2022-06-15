package com.hss.simple;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息消费者
 */
public class Recv {

    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //创建频道
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

        DefaultConsumer consumer = new DefaultConsumer(channel){
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[recv] msg:"+msg);
                System.out.println("消息的TagId："+envelope.getDeliveryTag());
                //false只确认签收当前的消息，设置为true的时候则代表签收该消费者所有未签收的消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        /*
        创建一个消息消费者
        第一个参数：队列名
        第二个参数代表是否自动确认收到消息，false代表手动编程来确认消息，这是MQ的推荐做法
        第三个参数要传入DefaultConsumer的实现类*/
        channel.basicConsume(QUEUE_NAME,false,consumer);

    }

    //3.x版本api，不推荐使用
    /*
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
    */
}
