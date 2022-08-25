package com.group3.service;

import com.group3.domain.User;
import com.group3.domain.UserDetail;
import com.group3.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testLogin(){
        User user = new User();
        user.setUserUsername("admin ");
        user.setUserPassword("admin");
        boolean flag = userService.login(user) != null;
        System.out.println(flag);
    }

    @Test
    public void testGetPhoneNumber(){
        String phone = userService.getPhone(1);
        System.out.println(phone);

    }

    @Test
    /**
     * test register
     */
    public void testRegister(){
        User user = new User();
        user.setUserUsername("test");
        user.setUserPassword("test");
        UserDetail userDetail = new UserDetail();
        userDetail.setUserDetailName("test");
        userDetail.setUserDetailEmail("test");
        userDetail.setUserDetailPhone("0006369");
        System.out.println(userService.register(user, userDetail));
    }

    /**
     * test select by phone
     */
    @Test
    public void testGetUserByPhone(){
        User userByPhone = userService.getUserByPhone("0493303279");
        System.out.println(userByPhone.toString());
    }
}
