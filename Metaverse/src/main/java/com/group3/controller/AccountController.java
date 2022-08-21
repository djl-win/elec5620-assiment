package com.group3.controller;

import com.group3.domain.Account;
import com.group3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.group3.controller.Code;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wallets")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 根据用户名查询,是否存在此账户
     * @param username 前端传入的用户名
     * @return 存在此用户则返回40011code，返回用户钱包的信息，前端转向前端具体钱包信息的页面；不存在则返回40010，前端转向创建钱包的页面
     */
    @GetMapping("{username}")
    public Result checkExistAccount(@PathVariable String username){

        Account account = accountService.selectAccountByUsername(username);
        int code = account != null ? Code.SELECT_OK : Code.SELECT_FAIL;
        String msg = account != null ? "You have already created wallet, next enter the wallet interface" : "Next to go to create a new Wallet";

        return new Result(account,code,msg);
    }

    /**
     * 根据前端传过来的用户名，帮助用户自动创建钱包
     * @param username token中的username
     * @return 用户的私钥
     */
    @PutMapping("{username}")
    public Result createWallet(@PathVariable String username){

        String priKey = accountService.createWalletByUsername(username);
        int code = priKey != null ? Code.UPDATE_OK : Code.UPDATE_FAIL;
        String msg = priKey != null ? "You have successfully created wallet, this is your private key" : "Fail to create";

        return new Result(priKey,code,msg);
    }
}
