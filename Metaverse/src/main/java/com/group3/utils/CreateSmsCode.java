package com.group3.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CreateSmsCode {

    private final String[] patch = {"000000","000000","0000","000","00","0",""};

    /**
     * 报错的话，去检查用户是否有详细的个人信息，比如手机号
     * @param tel
     * @return
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
     * 需加载进spring容器
     *
     * 从smsService的value = "VerifyCodeSpace"，的空间中取出生成的code值
     * return null if not have relative value
     */
    @Cacheable(value = "VerifyCodeSpace", key = "#tel")
    public String getCodeFromCache(String tel){

        return null;
    }


}
