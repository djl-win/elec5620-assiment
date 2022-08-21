package com.group3.dao;

import com.group3.domain.User;
import com.group3.domain.UserDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;


@SpringBootTest
@Transactional
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    /**
     * test login in dao
     */
    @Test
    public void testSignByUser(){
        User user = new User();
        user.setUserUsername("admin");
        user.setUserPassword("admin");
        boolean flag = userDao.signByUser(user) != null;
        System.out.println(flag);
    }

    /**
     * test checkAnySameValue
     */
    @Test
    public void testCheckAnySameValue(){
        User user = new User();
        user.setUserUsername("djlda");
        UserDetail userDetail = new UserDetail();
        userDetail.setUserDetailEmail("day.dong99@yahoo.com");
        userDetail.setUserDetailPhone("15542449708a");
        System.out.println(Arrays.toString(userDao.checkAnySameValue(user, userDetail)));
    }

    @Test
    public void testSelectUserByPhone(){
        User user = userDao.selectUserByPhone("0493303279");
        System.out.println(user.toString());
    }

    @Test
    public void testSelectByUsername(){
        System.out.println(userDao.selectByUserName("test01"));
    }
}
