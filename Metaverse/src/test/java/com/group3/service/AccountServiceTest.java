package com.group3.service;

import com.group3.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    /**
     * test weather the user has wallet or not?
     */
    @Test
    public void testSelectAccountByUsername(){
        Account admin = accountService.selectAccountByUsername("admin");
        System.out.println(admin);
    }

    @Test
    public void testCreateWalletByUsername(){
        String admin = accountService.createWalletByUsername("test01");
        System.out.println(admin);
    }
}
