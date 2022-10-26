package com.group3.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
public class FollowServiceTest {

    @Autowired
    private FollowService followService;

    @Test
    public void testFetchFollowsByUserId(){
        System.out.println(followService.fetchFollowsByUserId(1));
    }
}
