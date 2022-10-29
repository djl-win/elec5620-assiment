package com.group3.service.impl;

import com.group3.dao.AccountDao;
import com.group3.dao.LogDao;
import com.group3.dao.NftDao;
import com.group3.domain.Account;
import com.group3.domain.Log;
import com.group3.dto.TransactionInfo;
import com.group3.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private NftDao nftDao;


    @Override
    public int logCharge(Log log) {
        return logDao.chargeOperation(log);
    }

    @Override
    public ArrayList<Log> selectChargeInfoByPubKey(String accountPublicKey) {
        return logDao.fetchChargeInfoListByPubKey(accountPublicKey);
    }

    @Override
    public boolean logTransaction(int sellerId, int buyerId, int nftId, double price) {
        Account sellerAccount = accountDao.fetchByUserId(sellerId);
        Account buyerAccount = accountDao.fetchByUserId(buyerId);

        Log logSell = new Log();
        logSell.setLogPubKeyUserA(sellerAccount.getAccountPublicKey());
        logSell.setLogPubKeyUserB(buyerAccount.getAccountPublicKey());
        //A sell to B
        logSell.setLogType(3);
        logSell.setLogNftId(nftId);
        logSell.setLogPrice(price);
        logSell.setLogDescription("User A sells NFT with ID "+ nftId +" to user B for " + price+ " ATX");
        logSell.setLogStatus(1);
        int flagSell = logDao.transactionOperation(logSell);

        Log logBuy = new Log();
        logBuy.setLogPubKeyUserA(buyerAccount.getAccountPublicKey());
        logBuy.setLogPubKeyUserB(sellerAccount.getAccountPublicKey());
        //A buy from B
        logBuy.setLogType(2);
        logBuy.setLogNftId(nftId);
        logBuy.setLogPrice(price);
        logBuy.setLogDescription("User A buys NFT with ID "+ nftId +" from user B for " + price+ " ATX");
        logBuy.setLogStatus(1);
        int flagBuy = logDao.transactionOperation(logBuy);


        return flagSell == 1 && flagBuy == 1;
    }

    @Override
    public ArrayList<TransactionInfo> findTransaction() {
        ArrayList<TransactionInfo> transactionInfos = new ArrayList<>();

        ArrayList<Log> logs = logDao.selectLogByTypeAndStatus();

        for (Log log : logs) {
            TransactionInfo transactionInfo = new TransactionInfo();
            transactionInfo.setNft(nftDao.selectNftByNftId(log.getLogNftId()));
            transactionInfo.setLog(log);
            transactionInfos.add(transactionInfo);
        }
        return transactionInfos;
    }
}
