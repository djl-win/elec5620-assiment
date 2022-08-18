package com.group3.service.impl;

import com.group3.dao.AccountDao;
import com.group3.domain.Account;
import com.group3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public Account selectAccountByUsername(String username) {

        return accountDao.fetchByUsername(username);

    }
}
