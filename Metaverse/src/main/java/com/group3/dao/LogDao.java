package com.group3.dao;

import com.group3.domain.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface LogDao {


    int chargeOperation(@Param("log") Log log);

    /**
     * 通过用户的公钥查询他的充值信息，type=1，pubKey对应
     * @param accountPublicKey 用户公钥
     * @return log的集合
     */
    ArrayList<Log> fetchChargeInfoListByPubKey(@Param("pubKey") String accountPublicKey);
}
