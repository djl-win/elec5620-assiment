package com.group3.dao;

import com.group3.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    User signByUser(User user);

    String getPhoneNumber(@Param("id") int userid);

}
