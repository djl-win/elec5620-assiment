package com.group3.dto;

import lombok.Data;

/**
 * 用户登录信息的类
 */
@Data
public class SmsMessage {
    //或者用userDetail
    private String telephone;

    private String code;
}
