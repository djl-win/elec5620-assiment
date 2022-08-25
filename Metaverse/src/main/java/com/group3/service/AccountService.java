package com.group3.service;

import com.group3.domain.Account;
import com.group3.domain.Log;
import com.group3.domain.WalletPageInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = -1, rollbackFor = Exception.class)
public interface AccountService {

    /**
     * 根据控制层传过来的用户名，查询用户是否存在
     * @param username user表中的user_username
     * @return 返回用户信息，空则null
     */
    Account selectAccountByUsername(String username);

    /**
     * 根据控制层传过来的用户名，帮助用户创建钱包
     * @param username 用户名
     * @return 用户私钥
     */
    String createWalletByUsername(String username);


    /**
     *
     * @param amount 用户要充值的金额
     * @param username 用户的avatar
     * @return 改变的行数
     */
    boolean chargeAccount(double amount, String username);

    /**
     * 查询用户历史记录信息
     * @param username 用户名
     * @return 用户历史记录
     */
    WalletPageInfo<Log> selectHistoryByUsername(String username);
}
