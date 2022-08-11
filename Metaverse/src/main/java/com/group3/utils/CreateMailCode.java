package com.group3.utils;

import org.springframework.stereotype.Component;

@Component
public class CreateMailCode {

    public String CreateMailCode(){

        int mailCodeInt = (int) ((Math.random() * 9 + 1) * 100000);

        return String.valueOf(mailCodeInt);
    }

}
