# rabbitMQ
> rabbitMQ高并发消息中间件学习  
> 版本：RabbitMQ 3.6.5, Erlang 18.3
## 1 myRabbitMQ
### 1.1 简述
rabbitMQ 各种模式特性基本使用

### 1.2 详解
1. com.hss.simple(Hello World 模式)
2. com.hss.work(Work queues 模式)
3. com.hss.ps(Publish/Subscribe 模式)
4. com.hss.routing(Routing 模式)
5. com.hss.topic(Topic 模式)
6. com.hss.confirms(消息确认和退回，保证消息成功发送给MQ)
7. com.hss.tx(消息事务，不推荐使用，会导致MQ效率降低)
8. com.hss.workfair(保证消息发送顺序)

## 2 spring-rabbitmq
### 2.1 简述
rabbitMQ 与 spring 整合。  
包含 spring-rabbitmq-producers 和 spring-rabbitmq-consumers模块。

### 2.2 详情
1. 五大模式使用
2. 消息可靠性投递（确认机制、回退机制）
3. 保障消息被消费（手动签收机制）
4. TTL 过期队列
5. 死信队列（dead-letter-exchange）
6. 延迟队列（依赖过期队列和死信队列实现）

## 3 boot-rabbitmq
### 3.1 简述
rabbitMQ 与 springBoot 整合。  
包含 boot-rabbitmq-produces 和 boot-rabbitmq-consumers 模块。

### 3.2 详情
todo...