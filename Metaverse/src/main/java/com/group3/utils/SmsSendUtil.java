package com.group3.utils;

import com.group3.dto.SmsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

@Component
public class SmsSendUtil{

    @Autowired
    private CreateSmsCode smsCode;

    //Put the data into the cache and do not take it out
    // (if there is a value with key #tel in the space, put the corresponding value in or take it out)
    @CachePut(value = "VerifyCodeSpace", key = "#tel")
    public String sentCode(String tel) {
        return smsCode.createCode(tel);
    }

    /**
     * The code entered by the user is verified against the created code
     */
    public boolean checkCode(SmsMessage smsMessage) {

        //get code from cache
        String codeFromCache = smsCode.getVerificationCodeFromCache(smsMessage.getTelephone());

        //check whether the input code = or not code from cache
        return smsMessage.getCode().equals(codeFromCache);
    }
}
