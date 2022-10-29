package com.group3.dto;

import com.group3.domain.Nft;
import com.group3.domain.Order;
import com.group3.domain.UserDetail;
import lombok.Data;

@Data
public class OrderInfo {

    private Order order;

    private UserDetail userDetail;

    private Nft nft;

}
