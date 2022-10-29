package com.group3.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private int orderId;

    //buyer userid
    private int orderBuyerId;

    //seller userid
    private int orderSellerId;

    private int orderNftId;

    private double orderPrice;

    //order status 0 pending, status 1 agree, status 2 disagree
    private int orderStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+11")
    private Date orderDate;

}
