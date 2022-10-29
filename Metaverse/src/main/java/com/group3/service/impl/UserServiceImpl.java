package com.group3.service.impl;

import com.group3.dao.UserDao;
import com.group3.domain.User;
import com.group3.domain.UserDetail;
import com.group3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
//    @Cacheable(value = "users", key = "#user.userId")
    public User login(User user) {
        return userDao.signByUser(user);
    }

    @Override
//    @Cacheable(value = "verificationCode", key = "#userid")
    public String getPhone(int userid) {
        return userDao.getPhoneNumber(userid);
    }

    @Override
    public boolean register(User user, UserDetail userDetail) {

        //first check is there any same username, phone or email (can split it, but it is too maFanLe!)
        String[] strings = userDao.checkAnySameValue(user, userDetail);

        //if the length > 0, user exists, return false

        if (strings.length == 0) {

            int userIsOk = userDao.insertNewUser(user);

            //Return if it cannot be inserted correctly into the user table, otherwise only insert the userDetail
            if (userIsOk != 1 ) {

                return false;
            } else {

                int userid =  userDao.selectByUserName(user.getUserUsername());
                return userDao.insertNewUserDetail(userDetail, userid) == 1;
            }

        }
        return false;
    }

    @Override
    public User getUserByPhone(String userPhone) {
        return userDao.selectUserByPhone(userPhone);
    }
}
