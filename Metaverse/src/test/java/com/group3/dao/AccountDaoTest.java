package com.group3.dao;

import com.group3.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AccountDaoTest {

    @Autowired
    private AccountDao accountDao;

    @Test
    public void testSelectTest(){
        Account account = accountDao.selectTest();
        System.out.println(account.getAccountId());
        System.out.println(account.getAccountPublicKey());
        System.out.println(account.getAccountAvatar());
        System.out.println(account.getAccountBalance());
        System.out.println(account.getAccountDeleted());
        System.out.println(account.getAccountUserId());
    }

    /**
     * Test if the user has created a wallet?
     */
    @Test
    public void testFetchByUsername(){
        System.out.println(accountDao.fetchByUsername("test01"));
    }

    /**
     * Test if data can be inserted
     */
    @Test
    public void testInsertWalletByUsername(){
        Account account = new Account();
        account.setAccountPublicKey("dsadas");
        account.setAccountAvatar("Asdasda");
        account.setAccountBalance(0);
        account.setAccountDeleted(0);
        account.setAccountUserId(4);
        int flag = accountDao.insertWalletByUsername(account);
        System.out.println(flag);
    }

    /**
     * Test if you can recharge successfully
     */
    @Test
    public void testUpdateBalanceByAvatar(){
        int admin = accountDao.updateBalanceByAvatar(15.2, "admin");
        System.out.println(admin);

    }
}
