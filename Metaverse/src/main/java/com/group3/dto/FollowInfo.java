package com.group3.dto;

import com.group3.domain.Follow;
import com.group3.domain.Nft;
import com.group3.domain.User;
import com.group3.domain.UserDetail;
import lombok.Data;

@Data
public class FollowInfo {

    private Follow follow;

    private User user;

    private Nft nft;

    private UserDetail userDetail;

    //the number of this user followed
    private int followNumber;
}
