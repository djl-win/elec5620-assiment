package com.group3.dao;

import com.group3.domain.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest
@Transactional
public class LogDaoTest {

    @Autowired
    private LogDao logDao;

    @Test
    public void testChargeOperation(){
        Log log = new Log();
        log.setLogPubKeyUserA("dongjialede key");
        log.setLogType(1);
        log.setLogPrice(10.2);
        log.setLogDescription("djl Charged 10.2");
        log.setLogStatus(1);
        System.out.println(logDao.chargeOperation(log));
    }

    @Test
    public void testFetchChargeInfoListByPubKey(){
        ArrayList<Log> odsajhgfjkadfsgada = logDao.fetchChargeInfoListByPubKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALIklfxZ8L9SAjeGn7VZr8TM7qXjXo8S2n4SuKPc55XwggBqy6MkkWamJPwI1Ljh3+xXvm5CvAAk3MsRGWugptsCAwEAAQ==");

        for (Log log : odsajhgfjkadfsgada) {
            System.out.println(log);
        }

    }
}
