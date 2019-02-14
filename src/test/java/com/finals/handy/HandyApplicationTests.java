package com.finals.handy;

import com.finals.handy.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HandyApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private MessageService messageService;


    @Test
    public void testMessageService() {
//        for(int i=0;i<5;i++)
//        messageService.SendMessage(1, 2, "难受");
//        System.out.println(messageService.findIdAndMsgs(2));
//        System.out.println(messageService.findHistoryMessages(1, 2, 5));
//        System.out.println(messageService.findMessageNum(2));
//        System.out.println(messageService.findMessages_NoRead(1, 2));
//        System.out.println(messageService.setHaveRead(1, 2));
        messageService.deleteMessage(74);
        messageService.deleteMessage(73);
    }

}

