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
     * Needs to be loaded into the spring container
     *
     * Retrieve the generated code value from the value = "VerifyCodeSpace" space of the smsService
     * return null if not have relative value
     */
    @Cacheable(value = "MailCodeSpace", key = "#mailAddress")
    public String getMailVerificationCodeFromCache(String mailAddress){
        return null;
    }

}
