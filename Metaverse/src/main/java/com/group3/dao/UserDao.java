package com.group3.dao;

import com.group3.domain.User;
import com.group3.domain.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    User signByUser(User user);

    String getPhoneNumber(@Param("id") int userid);


    /**
     * 这样的查询效率极低，是全表扫描的状态，扫描完username 扫描email 扫描 phone
     * 优化在username建立索引，通过username查询完之后，
     * @param user
     * @param userDetail
     * @return
     */
    String[] checkAnySameValue(@Param("user") User user, @Param("userDetail") UserDetail userDetail);

    /**
     * 注册功能
     * @param user
     * @param
     * @return
     */
    int insertNewUser(@Param("user") User user);

    /**
     * 按用户名查找
     */
    int selectByUserName(@Param("user_username")String username);

    /**
     * 插入userdetail
     * @param userDetail
     * @param userid
     * @return
     */
    int insertNewUserDetail(@Param("userDetail") UserDetail userDetail, @Param("userid") int userid);

    /**
     *
     * @param userPhone phonenumber
     * @return user objection
     */
    User selectUserByPhone(@Param("phone")String userPhone);

    /**
     * select user by userid
     * @param userId userid
     * @return user objection
     */
    User selectUserByUserId(@Param("userid")int userId);
}
