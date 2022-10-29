package com.group3.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void testSelectAllOrder(){
        System.out.println(orderDao.selectAllOrder());
    }
}
