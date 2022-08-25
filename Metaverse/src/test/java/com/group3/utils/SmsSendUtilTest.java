package com.group3.utils;

import com.group3.dto.SmsMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmsSendUtilTest {

    @Autowired
    private SmsSendUtil smsSendUtil;

    @Test
    public void testSentCode(){
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setTelephone("100844650");
        String s = smsSendUtil.sentCode(smsMessage.getTelephone());
        System.out.println(s);
    }
}
