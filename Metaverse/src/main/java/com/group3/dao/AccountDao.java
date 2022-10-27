package com.group3.dao;

import com.group3.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

//mapperscan替换也可
@Mapper
public interface AccountDao {

    /**
     * test connection
     * @return
     */
    Account selectTest();

    /**
     * 根据user表中的user_username查询用户钱包信息
     * @return 用户钱包信息
     * @param username
     */
    Account fetchByUsername(@Param("username") String username);

    int insertWalletByUsername(@Param("account") Account account);


    /**
     * 为用户进行充值操作
     * @param amount 要充值的金额
     * @param username 用户的avatar,account表中的avatar就是username
     * @return 影响行数
     */
    int updateBalanceByAvatar(@Param("amount") double amount, @Param("username") String username);

    /**
     * search account by userid
     * @param userId userid
     * @return account
     */
    Account fetchByUserId(@Param("userid")int userId);
}
