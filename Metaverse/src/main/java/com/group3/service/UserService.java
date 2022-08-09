package com.group3.service;

import com.group3.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = -1, rollbackFor = Exception.class)
public interface UserService {

    User login(User user);
}
