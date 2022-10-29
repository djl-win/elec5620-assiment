package com.group3.dto;

import lombok.Data;

/**
 * Classes for user login information
 */
@Data
public class SmsMessage {
    //Or use userDetail
    private String telephone;

    private String code;
}
