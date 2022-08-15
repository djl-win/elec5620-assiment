package com.group3.dao;

import com.group3.domain.Account;
import org.apache.ibatis.annotations.Mapper;

//mapperscan替换也可
@Mapper
public interface AccountDao {

    /**
     * test connection
     * @return
     */
    Account selectTest();
}
