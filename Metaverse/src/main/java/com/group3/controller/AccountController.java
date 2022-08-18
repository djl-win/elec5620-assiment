package com.group3.controller;

import com.group3.domain.Account;
import com.group3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
