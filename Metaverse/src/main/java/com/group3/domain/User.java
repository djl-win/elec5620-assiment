package com.group3.domain;

import lombok.Data;

@Data
public class User {

    private int userId;

    private String userUsername;

    private String userPassword;

    private int userDeleted;
}
