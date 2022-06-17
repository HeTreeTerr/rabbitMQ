package com.hss.confirms;

import com.hss.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * confirm
 * 异步confirm模式：提供一个回调方法
 */
public class ConfirmSend3 {

    private static final String QUEUE_NAME="test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        boolean durable=true;//消息队列（queue）是否持久化
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        //生产者调用confirmSelect 将channel设置为confire模式
        channel.confirmSelect();

        //未确认的消息标识
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //通道添加监听
        channel.addConfirmListener(new ConfirmListener() {
            //没有问题的handleAck
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                //第二个参数 multiple 代表接收的数据是否为批量接收，一般我们用不到。
                if(multiple){
                    System.out.println("----------handleAck----------multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else{
                    System.out.println("----------handleAck----------multiple  false");
                    confirmSet.remove(deliveryTag);
                }
            }
            //handleNack
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("----------handleNack----------multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else{
                    System.out.println("----------handleNack----------multiple  false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        //接收回退消息的监听器（通常用于路由模式或通配符模式）
        channel.addReturnListener(new ReturnCallback() {
            @Override
            public void handle(Return r) {
                System.err.println("===========================");
                System.err.println("Return编码：" + r.getReplyCode() + "-Return描述:" + r.getReplyText());
                System.err.println("交换机:" + r.getExchange() + "-路由key:" + r.getRoutingKey() );
                System.err.println("Return主题：" + new String(r.getBody()));
                System.err.println("===========================");
            }
        });

        for(int i=0;i < 5;i++){
            String msgString = "hello confirm message["+i+"]";
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msgString.getBytes());
            confirmSet.add(seqNo);
        }

        // ConfirmListener() 是异步的，所以不能关闭连接
        /*channel.close();
        connection.close();*/

    }
}
