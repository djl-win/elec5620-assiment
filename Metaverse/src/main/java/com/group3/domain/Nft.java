package com.group3.domain;

import lombok.Data;

@Data
public class Nft {

    private int nftId;

    private String nftSignature;

    private String nftUrl;

    private double nftPrice;

    private String nftDescription;

    private int nftLikes;

    private int nftDeleted;

    private int nftVersion;

    private int nftUserId;

    private UserDetail userDetail;
}
