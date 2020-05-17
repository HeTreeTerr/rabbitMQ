package com.hss;

import com.hss.service.RabbitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootRabbitmqApplicationTests {

    @Autowired
    private RabbitService rabbitService;

    @Test
    public void contextLoads() {
        String message = "lalalla";
        rabbitService.sendPtoPMessage(message);
    }

    @Test
    public void sendFanoutMessage(){
        String message = "hahahah";
        rabbitService.sendFanoutMessage(message);
    }

}
