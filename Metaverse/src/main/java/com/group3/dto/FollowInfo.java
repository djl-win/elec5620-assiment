package com.group3.dto;

import com.group3.domain.Follow;
import com.group3.domain.Nft;
import com.group3.domain.User;
import com.group3.domain.UserDetail;
import lombok.Data;

@Data
public class FollowInfo {

    //nft owner follow list
    private Follow follow;

    //nft owner user info
    private User user;

    //nft owner nft
    private Nft nft;

    //nft owner userDetail
    private UserDetail userDetail;

    //nft owner privateKey
    private String privateKey;

    //buyer bid price
    private double bid;

    //the number of this user followed
    private int followNumber;
}
