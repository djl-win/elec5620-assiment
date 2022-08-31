package com.group3.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateUuid {

    public String newUuid(){
        String preUuid = UUID.randomUUID().toString();
        String changUuid = preUuid.substring(0,8)+preUuid.substring(9,13)+preUuid.substring(14,18)+preUuid.substring(19,23)+preUuid.substring(24);

        return changUuid;
    }
}
