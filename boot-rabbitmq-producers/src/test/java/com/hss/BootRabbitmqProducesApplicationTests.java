package com.hss;

import com.hss.service.RabbitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootRabbitmqProducesApplicationTests {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitService rabbitService;

    @Test
    public void sendPtoPMessage() throws InterruptedException {
        String message = "hello spring-boot,test rabbitMq P to P...";
        rabbitService.sendPtoPMessage(message);
        log.info("发送成功！");
        TimeUnit.SECONDS.sleep(10);

    }

    @Test
    public void sendFanoutMessage() throws InterruptedException {
        String message = "hello spring-boot,test rabbitMq PS...";
        rabbitService.sendFanoutMessage(message);
        log.info("发送成功！");
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void sendTopicMessage() throws InterruptedException {
        String message = "hello spring-boot,test rabbitMq TOPIC...";
        rabbitService.sendTopicMessage(message);
        log.info("发送成功！");
        TimeUnit.SECONDS.sleep(10);
    }

}
