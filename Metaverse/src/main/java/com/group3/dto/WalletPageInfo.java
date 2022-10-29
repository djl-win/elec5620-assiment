package com.group3.dto;

import com.group3.domain.Account;
import lombok.Data;

import java.util.ArrayList;

//Information returned from the login wallet page, including user wallet information, and top-up history
@Data
public class WalletPageInfo <T>{

    private Account account;

    private ArrayList<T> searchList;

}
