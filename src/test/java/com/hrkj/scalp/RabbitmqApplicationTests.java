package com.hrkj.scalp;

import com.hrkj.scalp.message.service.SendMassageService;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HrkjApplication.class)
public class RabbitmqApplicationTests {
    @Autowired
    private SendMassageService messageService;

    @Test
    public void send() {
        messageService.sendMsg("test_queue_1","hello i am delay msg");
    }

}

