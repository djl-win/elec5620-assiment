package com.group3.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CreateMailCode {

    public String CreateMailCode(){

        int mailCodeInt = (int) ((Math.random() * 9 + 1) * 100000);

        return String.valueOf(mailCodeInt);
    }

    /**
     * 需加载进spring容器
     *
     * 从smsService的value = "VerifyCodeSpace"，的空间中取出生成的code值
     * return null if not have relative value
     */
    @Cacheable(value = "MailCodeSpace", key = "#mailAddress")
    public String getMailVerificationCodeFromCache(String mailAddress){
        return null;
    }

}
