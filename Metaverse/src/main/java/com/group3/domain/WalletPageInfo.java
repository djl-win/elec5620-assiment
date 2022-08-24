package com.group3.domain;

import lombok.Data;

import java.util.ArrayList;

//登录钱包页面返回的信息，用户钱包信息，和充值历史
@Data
public class WalletPageInfo <T>{

    private Account account;

    private ArrayList<T> searchList;

}
