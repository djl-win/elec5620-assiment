package com.group3.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class FollowDaoTest {

    @Autowired
    private FollowDao followDao;

    @Test
    public void testFindAllFollowsByUserId(){

        System.out.println(followDao.findAllFollowsByUserId(1));
    }
}
