package com.group3.service;

import com.group3.domain.Account;
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
}
