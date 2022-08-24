package com.group3.service.impl;

import com.group3.dao.LogDao;
import com.group3.domain.Log;
import com.group3.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;


    @Override
    public int logCharge(Log log) {
        return logDao.chargeOperation(log);
    }

    @Override
    public ArrayList<Log> selectChargeInfoByPubKey(String accountPublicKey) {
        return logDao.fetchChargeInfoListByPubKey(accountPublicKey);
    }
}
