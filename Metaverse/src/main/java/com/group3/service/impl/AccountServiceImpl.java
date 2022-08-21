package com.group3.service.impl;

import com.group3.dao.AccountDao;
import com.group3.dao.UserDao;
import com.group3.domain.Account;
import com.group3.service.AccountService;
import com.group3.utils.BlockChainKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private BlockChainKeys blockChainKeys;

    @Override
    public Account selectAccountByUsername(String username) {

        return accountDao.fetchByUsername(username);

    }

    @Override
    public String createWalletByUsername(String username) {

        String pubKey = null;
        String priKey = null;

        try {
            String[] strings = blockChainKeys.generatePubAndPriKeys();
            pubKey = strings[0];
            priKey = strings[1];
        } catch (Exception e) {
            return null;
        }

        Account account = new Account();
        account.setAccountPublicKey(pubKey);
        account.setAccountAvatar(username);
        account.setAccountBalance(0);
        account.setAccountDeleted(0);
        account.setAccountUserId(userDao.selectByUserName(username));

        int flag = accountDao.insertWalletByUsername(account);

        return flag == 0 ? null : priKey;
    }
}
