package com.group3.service.impl;

import com.group3.dao.AccountDao;
import com.group3.dao.UserDao;
import com.group3.domain.Account;
import com.group3.domain.Log;
import com.group3.dto.FollowInfo;
import com.group3.dto.WalletPageInfo;
import com.group3.service.AccountService;
import com.group3.service.LogService;
import com.group3.utils.BlockChainKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private BlockChainKeys blockChainKeys;

    @Autowired
    private LogService logService;

    @Override
    public WalletPageInfo<Log> selectHistoryByUsername(String username) {

        //Classes that encapsulate wallet owner and operation log information
        WalletPageInfo<Log> logWalletPageInfo = new WalletPageInfo<>();

        Account account = accountDao.fetchByUsername(username);

        logWalletPageInfo.setAccount(account);

        ArrayList<Log> logList = logService.selectChargeInfoByPubKey(account.getAccountPublicKey());

        logWalletPageInfo.setSearchList(logList);

        return logWalletPageInfo;

    }

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

    @Override
    public boolean chargeAccount(double amount, String username){
        int flag = 0;

        //Logging
        Log log = new Log();

        //The user's public key
        String accountPublicKey = accountDao.fetchByUsername(username).getAccountPublicKey();

        log.setLogPubKeyUserA(accountPublicKey);

        //log type is charge
        log.setLogType(1);

        //the amount to charge
        log.setLogPrice(amount);
        //description in this charge
        log.setLogDescription(username + " " + "charged " + amount + " ATX");

        //set status 1 if charge success
        log.setLogStatus(1);

        try {

            //first charge to account table
            //next record log
            flag = accountDao.updateBalanceByAvatar(amount,username);
            //Test Exceptions
            //int a = 1/0;

        } catch (Exception e) {

            //If the error is reported, set the status to 0 and add it
            e.printStackTrace();
            //Exceptions are not thrown after try catch and need to be set manually. Three types of rollback (auto manual partial)
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.setLogStatus(0);
            return false;

        } finally {
            logService.logCharge(log);
        }

        return flag == 1;
    }


    @Override
    public boolean verifyWalletByPublicKey(FollowInfo followInfo, int userId) {

        //1.get user public key, search table account by userid
        Account account = accountDao.fetchByUserId(userId);

        String pubkey = account.getAccountPublicKey();

        //2.get nft signature
        String nftSignature = followInfo.getNft().getNftSignature();

        //3.encrypt signature by public key
        try {
            System.out.println(pubkey);
            PublicKey publicKey = blockChainKeys.getPublicKey(pubkey);

            String after =  blockChainKeys.encrypt(nftSignature, publicKey);

            PrivateKey privateKey =  blockChainKeys.getPrivateKey(followInfo.getPrivateKey());

            //4.decrypt signature by private key
            String decrypt = blockChainKeys.decrypt(after, privateKey);

            //5.verify
            if(!decrypt.equals(nftSignature)){
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
