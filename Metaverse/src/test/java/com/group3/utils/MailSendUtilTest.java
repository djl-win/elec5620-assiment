package com.group3.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailSendUtilTest {

    @Autowired
    private MailSendUtil mailSendUtil;

    @Test
    public void testSentMailCode(){

        System.out.println(mailSendUtil.sentMailCode("395763745@qq.com"));

    }


}
