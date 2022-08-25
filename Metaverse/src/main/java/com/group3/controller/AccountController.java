package com.group3.controller;

import com.alibaba.fastjson.JSONObject;
import com.group3.domain.Account;
import com.group3.domain.Log;
import com.group3.domain.WalletPageInfo;
import com.group3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/wallets")
public class AccountController {
    //regx
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    @Autowired
    private AccountService accountService;

    /**
     * 根据用户名查询,是否存在此账户
     * @param username 前端传入的用户名
     * @return 存在此用户则返回40011code，返回用户钱包的信息，前端转向前端具体钱包信息的页面；不存在则返回40010，前端转向创建钱包的页面
     */
    @GetMapping
    public Result checkHistory(String username){

        WalletPageInfo<Log> logWalletPageInfo = accountService.selectHistoryByUsername(username);
        int code = logWalletPageInfo != null ? Code.SELECT_OK : Code.SELECT_FAIL;
        String msg = logWalletPageInfo != null ? "Search success" : "Search error";

        return new Result(logWalletPageInfo,code,msg);
    }

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

    /**
     * 接受前端传入的amount金额，帮助用户进行充值操作
     * @param input (前端传入的充值数量，和用户的用户名)
     * @return 成功与否
     */
    @PutMapping
    public Result increaseBalance(@RequestBody String input){

        JSONObject jsonObject = JSONObject.parseObject(input);

        //这里可以判断字符串是不是由数字组成的，不是的话就直接返回错误代码，
        String originalAmount = jsonObject.getString("amount");

        //amount表中的avatar是和username一样的，直接匹配就行
        String username = jsonObject.getString("username");

        boolean matches = pattern.matcher(originalAmount).matches();
        if(matches){

            //if match number, then to charge operation
            double amount = Double.parseDouble(originalAmount);

            //judge success or not
            boolean flag = accountService.chargeAccount(amount, username);

            //code
            int code = flag ? Code.UPDATE_OK: Code.UPDATE_FAIL;

            //msg
            String msg = flag ? "Congratulations, you have successfully charged" : "Fail to charge";

            return new Result(flag,code,msg);
        }else {
            return new Result(null,Code.UPDATE_FAIL,"wrong input");
        }


    }
}
