package com.group3.dao;

import com.group3.domain.Log;
import com.group3.dto.TransactionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface LogDao {


    int chargeOperation(@Param("log") Log log);

    int transactionOperation(@Param("log") Log log);

    /**
     * Query the user's top-up information by his public key, type=1, use pubKey to correspond to.
     * @param accountPublicKey User public key
     * @return The set of log
     */
    ArrayList<Log> fetchChargeInfoListByPubKey(@Param("pubKey") String accountPublicKey);

    /**
     * search all transaction info type =2 status =1
     * @return ArrayList<Log>
     */
    ArrayList<Log> selectLogByTypeAndStatus();
}
