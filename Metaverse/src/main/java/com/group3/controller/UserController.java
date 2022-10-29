package com.group3.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.group3.common.Code;
import com.group3.common.Result;
import com.group3.dto.RegisterInfo;
import com.group3.dto.SmsMessage;
import com.group3.domain.User;
import com.group3.domain.UserDetail;
import com.group3.service.UserService;
import com.group3.utils.MailSendUtil;
import com.group3.utils.SmsSendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SmsSendUtil smsSendUtil;

    @Autowired
    private MailSendUtil mailSendUtil;

    /**
     * login
     */
    @PostMapping
    public Result login(@RequestBody User userinfo, HttpServletRequest request) {

        Integer code = 40010;

        String msg = "wrong username or password";

        System.out.println(userinfo.getUserUsername() + " login");

        User login = userService.login(userinfo);

        boolean flag = login != null;

        if (flag) {
            //Get the current user's cell phone number by id and put it into the cache
            String phone = userService.getPhone(login.getUserId());

            //The cell phone number is stored in the session and passed to
            // the next method of checking the verification code,
            // because the operation of checking the verification code,
            // there is no front-end incoming cell phone number
            HttpSession session = request.getSession();
            session.setAttribute("userPhone", phone);

            //CODE can be sent to the user, where it returns data directly
            String newCode = smsSendUtil.sentCode(phone);

            //Send code to return value
            return new Result(newCode,40011,"login successfully, next to enter the code");
        }

        return new Result(null,code,msg);
    }

    /**
     * check the code if user enter ture username and password
     */
    @GetMapping("{inputCode}")
    public Result checkInputCode(@PathVariable String inputCode, HttpServletRequest request){

        //Unresolved security issues, limiting the number of times users can get the verification code,
        // and the expiration time of the verification code

        SmsMessage smsMessage = new SmsMessage();

        //get userPhone from session
        HttpSession session = request.getSession();
        String userPhone = (String) session.getAttribute("userPhone");
        smsMessage.setTelephone(userPhone);
        smsMessage.setCode(inputCode);

        //check code
        boolean flag = smsSendUtil.checkCode(smsMessage);

        //if true code, put user to session to release interceptor
        if(flag){
            //Sessions stored after login, available cache optimization
            User user = userService.getUserByPhone(userPhone);
            session.setAttribute("user", user);

//            return new Result(true,Code.SELECT_OK, "Login successfully");
            return new Result(user.getUserUsername(), Code.SELECT_OK, "Login successfully");
        }


        return new Result(false,Code.SELECT_FAIL, "Wrong code or Verification code expired, retry login");
    }

    /**
     * sign up : sent email verification code
     */
    @GetMapping
    public Result setMailVerificationCode(String emailAddress){

        String codeMail = mailSendUtil.sentMailCode(emailAddress);
        if (codeMail != null){
            return new Result(true,Code.SELECT_OK,"code has sent to your email");
        }

        return new Result(false,Code.SELECT_FAIL, "fail to send to you");

    }

    /**
     * register the user, and check the mail code
     *
     */
    @PutMapping
    public Result signUp(@RequestBody String input){


        //Use Alibaba's fastjson to get email verification code
        //Commonly used api: https://blog.csdn.net/weixin_41251135/article/details/110231280
        JSONObject jsonObject = JSONObject.parseObject(input);

        String mailCode = jsonObject.getString("mailCode");

        User user = JSON.parseObject(input, User.class);

        UserDetail userDetail = JSON.parseObject(input, UserDetail.class);

        RegisterInfo registerInfo = new RegisterInfo();

        //Package mail code
        registerInfo.setMailCode(mailCode);
        //Package user
        registerInfo.setUser(user);
        //Package user detail
        registerInfo.setUserDetail(userDetail);

        String email = registerInfo.getUserDetail().getUserDetailEmail();

        String checkCode = registerInfo.getMailCode();

        //first check the mail code
        boolean check = mailSendUtil.checkMailCode(email,checkCode);

        //If correct, proceed to the next step, wrong code return 60010
        if(!check){
            return new Result(false,Code.CODE_FAIL,"Please check you mail code!");
        }

        boolean registerOk =  userService.register(registerInfo.getUser(),registerInfo.getUserDetail());

        //if registerOK fine, congratulations. if not same username or same phone or same email
        if(registerOk){
            return new Result(true, Code.INSERT_OK, "Congratulations! You have become our member.");
        }
        return new Result(false,Code.INSERT_FAIL, "Unluckily, you used the same username or email or phone with others, retry again");
    }
}

