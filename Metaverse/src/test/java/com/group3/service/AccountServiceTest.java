package com.group3.service;

import com.group3.domain.Account;
import com.group3.domain.Log;
import com.group3.domain.WalletPageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest
//@Transactional
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    /**
     * test weather the user has wallet or not?
     */
    @Test
    public void testSelectAccountByUsername(){
//        WalletPageInfo<Log> walletInfo = accountService.selectAccountByUsername("admin");
//        System.out.println(walletInfo.getAccount());
//
//        ArrayList<Log> searchList = walletInfo.getSearchList();
//        for (Log log : searchList) {
//            System.out.println(log);
//        }

    }

    @Test
    public void testCreateWalletByUsername(){
        String admin = accountService.createWalletByUsername("test01");
        System.out.println(admin);
    }

    @Test
    public void testChargeAccount(){
        boolean admin = accountService.chargeAccount(100, "admin");
        System.out.println(admin);

    }
}
