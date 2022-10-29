package com.group3.dao;

import com.group3.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

//Replacing it with mapperscan is also possible.
@Mapper
public interface AccountDao {

    /**
     * test connection
     * @return account
     */
    Account selectTest();

    /**
     * Query user wallet information based on user_username in user table
     * @return User Wallet Information
     * @param username username
     */
    Account fetchByUsername(@Param("username") String username);

    int insertWalletByUsername(@Param("account") Account account);


    /**
     * Performing top-up operations for users
     * @param amount Amount to be recharged
     * @param username User's avatar. The avatar in the account table is the username.
     * @return Number of rows affected
     */
    int updateBalanceByAvatar(@Param("amount") double amount, @Param("username") String username);

    /**
     * search account by userid
     * @param userId userid
     * @return account
     */
    Account fetchByUserId(@Param("userid")int userId);

    /**
     * deduct account balance by userid
     * @param buyerId buyid
     * @param bid price need to deduct
     * @return 1 or 0
     */
    int updateBalanceByUserId(@Param("userid")int buyerId, @Param("bid")double bid);

    /**
     * increase account balance by userid
     * @param buyerId buyid
     * @param bid price need to increase
     * @return 1 or 0
     */
    int increaseBalanceByUserId(@Param("userid")int buyerId, @Param("bid")double bid);
}
