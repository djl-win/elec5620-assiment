package com.group3.service;

import com.group3.domain.SmsMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmsServiceTest {

    @Autowired
    private SmsService smsService;

    @Test
    public void testSentCode(){
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setTelephone("100844650");
        String s = smsService.sentCode(smsMessage.getTelephone());
        System.out.println(s);
    }
}
