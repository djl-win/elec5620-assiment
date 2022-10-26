package com.group3.dao;

import com.group3.domain.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDetailDao {

    UserDetail selectUserDetailByUserId(@Param("userid") int userId);
}
