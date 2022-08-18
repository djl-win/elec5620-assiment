package com.group3.service;

import com.group3.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    /**
     * test weather the user has wallet or not?
     */
    @Test
    public void testSelectAccountByUsername(){
        Account admin = accountService.selectAccountByUsername("test01");
        System.out.println(admin);

    }
}
