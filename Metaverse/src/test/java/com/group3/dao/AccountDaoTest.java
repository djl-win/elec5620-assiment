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
     * 测试用户是否已经创建钱包？
     */
    @Test
    public void testFetchByUsername(){
        System.out.println(accountDao.fetchByUsername("test01"));
    }

    /**
     * 测试是否可以插入数据
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
     * 测试是否可以充值成功
     */
    @Test
    public void testUpdateBalanceByAvatar(){
        int admin = accountDao.updateBalanceByAvatar(15.2, "admin");
        System.out.println(admin);

    }
}
