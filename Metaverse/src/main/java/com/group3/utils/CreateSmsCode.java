package com.group3.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CreateSmsCode {

    private final String[] patch = {"000000","000000","0000","000","00","0",""};

    /**
     * If an error is reported, go check whether the user has detailed personal information, such as cell phone number
     * @param tel telephone
     * @return code of verification
     */
    public String createCode(String tel){
        int hash = tel.hashCode();
        int encryption = 1008611;
        long encryptResult = hash ^ encryption;
        long currentTime = System.currentTimeMillis();
        encryptResult = encryptResult ^ currentTime;
        long result = encryptResult % 1000000;
        result = result < 0 ? -result : result;
        String code = result + "";
        int len = code.length();
        return patch[len] + code;
    }

    /**
     * Needs to be loaded into the spring container
     *
     * Take the generated code value from the value = "VerifyCodeSpace", space of the smsService
     * return null if not have relative value
     */
    @Cacheable(value = "VerifyCodeSpace", key = "#tel")
    public String getVerificationCodeFromCache(String tel){

        return null;
    }


}
