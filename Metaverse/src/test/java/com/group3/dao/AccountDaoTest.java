package com.group3.dao;

import com.group3.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
     * 测试用户是否已经创建钱包？
     */
    @Test
    public void testFetchByUsername(){
        System.out.println(accountDao.fetchByUsername("test01"));
    }
}
