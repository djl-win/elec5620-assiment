package com.group3.dao;

import com.group3.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

//mapperscan替换也可
@Mapper
public interface AccountDao {

    /**
     * test connection
     * @return
     */
    Account selectTest();

    /**
     * 根据user表中的user_username查询用户钱包信息
     * @return 用户钱包信息
     * @param username
     */
    Account fetchByUsername(@Param("username") String username);
}
