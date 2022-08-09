package com.group3.service.impl;

import com.group3.dao.UserDao;
import com.group3.domain.User;
import com.group3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(User user) {
        return userDao.signByUser(user);
    }
}
