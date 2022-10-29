package com.group3.service;


import com.group3.domain.Log;
import com.group3.dto.TransactionInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

//As a separate transaction, it does not participate in the rollback of the recharge operation,
// recharge failure is also added to the database, and status is set to 0
@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
public interface LogService {

    /**
     *
     *  userPubKey The user's public key
     *  logType Charge type:1 for recharge, 2 for A buy B, 3 for B buy A
     *  amount Recharge amount
     *  logDescription Recharge Description
     *  logStatus Charge status, 1 is success, 0 is failure
     * @return Number of rows changed by recharge
     */
    int logCharge(Log log);

    /**
     * Query the recharge log information, type=1, regardless of the recharge status,
     * the front-end page shows recharge failure or success
     * @param accountPublicKey User public key
     * @return Log of user recharge
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
