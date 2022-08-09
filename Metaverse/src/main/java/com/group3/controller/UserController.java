package com.group3.controller;

import com.group3.domain.User;
import com.group3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * login
     */
    @PostMapping
    public Result login(@RequestBody User userinfo, HttpServletRequest request){


        System.out.println(userinfo.getUserUsername() + " login");

        User login = userService.login(userinfo);

        HttpSession session = request.getSession();
        session.setAttribute("user", login);

        boolean flag = login != null;
        Integer code = flag ? Code.SELECT_OK:Code.SELECT_FAIL;
        String msg = flag ? "login success":"wrong username or password";

        return new Result(flag,code,msg);

    }
}
