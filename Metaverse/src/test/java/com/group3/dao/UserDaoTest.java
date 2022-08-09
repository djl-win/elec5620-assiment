package com.group3.dao;

import com.group3.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


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
}
