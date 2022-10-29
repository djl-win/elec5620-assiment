package com.group3.service;


import com.group3.domain.Log;
import com.group3.dto.TransactionInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

//作为单独事务，不参与充值业务的回滚，充值失败也要添加到数据库，status设置为0
@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
public interface LogService {

    /**
     *
     *  userPubKey 用户的公钥
     *  logType 充值类型，1为充值 2为A买B 3为A卖B
     *  amount 充值金额
     *  logDescription 充值描述
     *  logStatus 充值状态 1为成功，0为失败
     * @return 充值改变的行数
     */
    int logCharge(Log log);

    /**
     * 查询充值日志的信息，type=1，充值状态不管，前端页面显示充值失败或者成功
     * @param accountPublicKey 用户公钥
     * @return 用户充值的log
     */
    ArrayList<Log> selectChargeInfoByPubKey(String accountPublicKey);

    /**
     * log transaction
     * @param sellerId seller id
     * @param buyerId buyer id
     * @param nftId buyer id
     * @return affect row
     */
    boolean logTransaction(int sellerId, int buyerId, int nftId, double price);

    /**
     * find Transaction, table log ,type = 2, status = 1
     * @return ArrayList<TransactionInfo>
     */
    ArrayList<TransactionInfo> findTransaction();
}
