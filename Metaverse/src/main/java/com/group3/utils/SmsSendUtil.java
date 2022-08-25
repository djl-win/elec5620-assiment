package com.group3.utils;

import com.group3.dto.SmsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

@Component
public class SmsSendUtil{

    @Autowired
    private CreateSmsCode smsCode;

    //把数据放入缓存，不取出来（如果空间中有key为#tel的值就把，对应的值存进去或者取出来）
    @CachePut(value = "VerifyCodeSpace", key = "#tel")
    public String sentCode(String tel) {
        return smsCode.createCode(tel);
    }

    /**
     * 用户输入的code和这个创建出来的code做校验
     */
    public boolean checkCode(SmsMessage smsMessage) {

        //get code from cache
        String codeFromCache = smsCode.getVerificationCodeFromCache(smsMessage.getTelephone());

        //check whether the input code = or not code from cache
        return smsMessage.getCode().equals(codeFromCache);
    }
}
