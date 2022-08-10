package com.group3.controller;

import com.group3.domain.SmsMessage;
import com.group3.domain.User;
import com.group3.service.SmsService;
import com.group3.service.UserService;
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
    private SmsService smsService;

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
            //获取当前用户手机号通过id，放入缓存
            String phone = userService.getPhone(login.getUserId());

            //手机号存入session，传递给下一个校验验证码的方法，因为校验验证码的操作，没有前端传入的手机号
            HttpSession session = request.getSession();
            session.setAttribute("userPhone", phone);

            //CODE 可以发送给用户，此处直接返回data
            String newCode = smsService.sentCode(phone);

            //发送code到返回值
            return new Result(newCode,40011,"login successfully, next to enter the code");
        }

        return new Result(null,code,msg);
    }

    /**
     * check the code if user enter ture username and password
     */
    @GetMapping("{inputCode}")
    public Result checkInputCode(@PathVariable String inputCode, HttpServletRequest request){

        //安全问题未解决，限制用户获取验证码次数，以及验证码过期时间

        SmsMessage smsMessage = new SmsMessage();

        //get userPhone from session
        HttpSession session = request.getSession();
        String userPhone = (String) session.getAttribute("userPhone");
        smsMessage.setTelephone(userPhone);
        smsMessage.setCode(inputCode);

        //check code
        boolean flag = smsService.checkCode(smsMessage);

        //if true code, put user to session to release interceptor
        if(flag){
           session.setAttribute("user", "temporary user, if have the chance, correct this information to user");
           return new Result(true,Code.SELECT_OK, "Login successfully");
        }

        return new Result(false,Code.SELECT_FAIL, "Wrong code, retry login");
    }
}

