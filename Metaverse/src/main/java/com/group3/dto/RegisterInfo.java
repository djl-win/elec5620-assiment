package com.group3.dto;

import com.group3.domain.User;
import com.group3.domain.UserDetail;
import lombok.Data;

@Data
public class RegisterInfo {

    private User user;

    private UserDetail userDetail;

    private String mailCode;
}
