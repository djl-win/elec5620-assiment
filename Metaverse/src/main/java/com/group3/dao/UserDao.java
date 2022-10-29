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
     * Such a query is extremely inefficient because it is a full table scan.
     * 1.scan username. 2. scan email 3.scan phone
     * Optimization: Create an index at username, and query through username.
     * @param user
     * @param userDetail
     * @return
     */
    String[] checkAnySameValue(@Param("user") User user, @Param("userDetail") UserDetail userDetail);

    /**
     * Registration function
     * @param user
     * @param
     * @return
     */
    int insertNewUser(@Param("user") User user);

    /**
     * Search by username
     */
    int selectByUserName(@Param("user_username")String username);

    /**
     * Insert user detail
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
