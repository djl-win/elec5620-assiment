package com.group3.domain;

import lombok.Data;

@Data
public class RegisterInfo {

    private User user;

    private UserDetail userDetail;

    private String mailCode;
}
