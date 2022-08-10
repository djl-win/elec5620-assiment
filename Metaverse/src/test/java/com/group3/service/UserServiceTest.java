package com.group3.service;

import com.group3.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
}
