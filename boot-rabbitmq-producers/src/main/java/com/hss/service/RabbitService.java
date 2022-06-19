package com.hss.service;

public interface RabbitService {
    /**
     * 点对点，直接发送至队列
     * @param message
     */
    void sendPtoPMessage(String message);

    /**
     * fanout 无论routingKey是否一致或是为空，都会发送给所有队列
     * @param message
     */
    void sendFanoutMessage(String message);

    /**
     * topic 通配符模式
     * @param message
     */
    void sendTopicMessage(String message);
}
