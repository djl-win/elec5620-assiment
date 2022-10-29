package com.group3.service;

import com.group3.dto.FollowInfo;
import com.group3.dto.OrderInfo;
import com.group3.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional(timeout = -1, rollbackFor = {Exception.class, BusinessException.class})
public interface OrderService {

    /**
     * new order bid
     * @param followInfo nft and seller info
     * @param buyerId buyerid
     * @return ok or not
     */
    int newOrder(FollowInfo followInfo, int buyerId);


    /**
     * find Orders By Seller Or Buyer, use type to Distinguish bid(1) or quotation(2)
     * @param type 1 or 2
     * @param userId userid
     * @return order info
     */
    ArrayList<OrderInfo> findOrdersBySellerOrBuyer(int type,int userId);

    /**
     * agree transaction
     * @param sellerId seller id
     * @param buyerId buyer id
     * @param nftId nft id
     * @param orderId order id
     * @return true or not
     */
    boolean agreeTransaction(int sellerId, int buyerId, int nftId, int orderId);

    /**
     * reject transaction
     * @param buyerId buyer id
     * @param orderId order id
     */
    void rejectTransaction(int buyerId, int orderId);
}
