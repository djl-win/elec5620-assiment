package com.group3.controller;

import com.alibaba.fastjson.JSONObject;
import com.group3.common.Code;
import com.group3.common.Result;
import com.group3.domain.Account;
import com.group3.domain.Log;
import com.group3.domain.User;
import com.group3.dto.FollowInfo;
import com.group3.dto.WalletPageInfo;
import com.group3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/wallets")
public class AccountController {
    //regx Regular expressions for positive numbers
    private Pattern pattern = Pattern.compile("^(0\\.0*[1-9]+[0-9]*$|[1-9]+[0-9]*\\.[0-9]*[0-9]$|[1-9]+[0-9]*$)");

    @Autowired
    private AccountService accountService;

    /**
     * Search by username to see if this account exists
     *  username The username passed in from the front end
     * @return If this user exists, 40011 code is returned, which returns the user's wallet information,
     * and the front-end moves to the front-end specific wallet information page;
     * if it does not exist, 40010 is returned, and the front-end moves to the wallet creation page
     */
    @GetMapping
    public Result checkHistory( HttpServletRequest request){

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String username  = user.getUserUsername();

        WalletPageInfo<Log> logWalletPageInfo = accountService.selectHistoryByUsername(username);
        int code = logWalletPageInfo != null ? Code.SELECT_OK : Code.SELECT_FAIL;
        String msg = logWalletPageInfo != null ? "Search success" : "Search error";

        return new Result(logWalletPageInfo,code,msg);
    }

    /**
     * Search by username to see if this account exists
     *  username The username passed in from the front end
     * @return If this user exists, 40011 code is returned, which returns the user's wallet information,
     * and the front-end moves to the front-end specific wallet information page;
     * if it does not exist, 40010 is returned, and the front-end moves to the wallet creation page
     */
    @PostMapping
    public Result checkExistAccount(HttpServletRequest request){

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String username  = user.getUserUsername();

        Account account = accountService.selectAccountByUsername(username);
        int code = account != null ? Code.SELECT_OK : Code.SELECT_FAIL;
        String msg = account != null ? "You have already created wallet, next enter the wallet interface" : "Next to go to create a new Wallet";

        return new Result(account,code,msg);
    }


    /**
     * Helps users create wallets automatically based on the username passed from the front-end
     * username The username in token
     * @return The user's private key
     */
    @RequestMapping("/create")
    @PutMapping
    public Result createWallet(HttpServletRequest request){

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String username  = user.getUserUsername();

        String priKey = accountService.createWalletByUsername(username);
        int code = priKey != null ? Code.UPDATE_OK : Code.UPDATE_FAIL;
        String msg = priKey != null ? "You have successfully created wallet, this is your private key" : "Fail to create";

        return new Result(priKey,code,msg);
    }

    /**
     * Accept the amount passed in from the front-end and help the user to perform the recharge operation
     * @param input (The number of recharges passed in from the front end, and the user's username)
     * @return Success or Failure
     */
    @PutMapping
    public Result increaseBalance(@RequestBody String input, HttpServletRequest request){

        JSONObject jsonObject = JSONObject.parseObject(input);

        //Here you can determine whether the string is made up of numbers or not,
        // and return the error code directly if it is not.
        String originalAmount = jsonObject.getString("amount");

        //The avatar in the amount table is the same as username, just match it directly
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String username  = user.getUserUsername();

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
            return new Result(null,Code.UPDATE_FAIL,"Wrong input, please enter positive integers");
        }
    }

    /**
     * Verify the user's wallet with private key, encrypted and decrypted
     * @param followInfo Information such as nft and private key
     * @param request Get the current user id and public key
     * @return success or not
     */
    @PostMapping("/verify")
    public Result verifyWallet(@RequestBody FollowInfo followInfo,HttpServletRequest request){
        //Get user id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int userId = user.getUserId();

        boolean flag = accountService.verifyWalletByPublicKey(followInfo,userId);

        if(flag){
            return new Result(true, Code.SELECT_OK,"success");
        }else {
            return new Result(false, Code.SELECT_FAIL, "Please enter the correct Private key!");
        }
    }
}
