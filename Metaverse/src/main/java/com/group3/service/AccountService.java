package com.group3.service;

import com.group3.domain.Account;
import com.group3.domain.Log;
import com.group3.dto.FollowInfo;
import com.group3.dto.WalletPageInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = -1, rollbackFor = Exception.class)
public interface AccountService {

    /**
     * Query the existence of the user based on the user name passed from the control layer
     * @param username user_username in user table
     * @return Return user information, null if empty
     */
    Account selectAccountByUsername(String username);

    /**
     * Help the user create a wallet based on the username passed through the control layer
     * @param username User Name
     * @return User Private Key
     */
    String createWalletByUsername(String username);


    /**
     *
     * @param amount Amount to be topped up by the user
     * @param username User's avatar
     * @return Number of rows changed
     */
    boolean chargeAccount(double amount, String username);

    /**
     * Query user history information
     * @param username User Name
     * @return User History Log
     */
    WalletPageInfo<Log> selectHistoryByUsername(String username);

    /**
     * Verify that the user's private key is correct by the user's id, nft signature, and public key
     * @param followInfo nft signature
     * @param userId userid
     * @return success or not
     */
    boolean verifyWalletByPublicKey(FollowInfo followInfo, int userId);
}
