package com.group3.service.impl;

import com.group3.domain.SmsMessage;
import com.group3.service.SmsService;
import com.group3.utils.CreateSmsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private CreateSmsCode smsCode;

    @Override
    //把数据放入缓存，不取出来（如果空间中有key为#tel的值就把，对应的值存进去或者取出来）
    @CachePut(value = "VerifyCodeSpace", key = "#tel")
    public String sentCode(String tel) {
        return smsCode.createCode(tel);
    }

    @Override
    /**
     * 用户输入的code和这个创建出来的code做校验
     */
    public boolean checkCode(SmsMessage smsMessage) {

        //get code from cache
        String codeFromCache = smsCode.getCodeFromCache(smsMessage.getTelephone());

        //check whether the input code = or not code from cache
        return codeFromCache.equals(smsMessage.getCode());
    }
}
