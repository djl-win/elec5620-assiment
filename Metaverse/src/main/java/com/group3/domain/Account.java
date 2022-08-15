package com.group3.domain;

import lombok.Data;

@Data
public class Account {

    private int accountId;

    private String accountPublicKey;

    private String accountAvatar;

    private double accountBalance;

    private int accountDeleted;

    private int accountUserId;
}
