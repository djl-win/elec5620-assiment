package com.group3.service.impl;

import com.group3.common.Code;
import com.group3.dao.*;
import com.group3.domain.Account;
import com.group3.domain.Nft;
import com.group3.domain.Order;
import com.group3.domain.User;
import com.group3.dto.FollowInfo;
import com.group3.dto.OrderInfo;
import com.group3.exception.BusinessException;
import com.group3.service.LogService;
import com.group3.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDetailDao userDetailDao;

    @Autowired
    private NftDao nftDao;

    @Autowired
    private LogService logService;

    @Override
    public int newOrder(FollowInfo followInfo, int buyerId) {
        //check the balance of buyer
        Account buyer = accountDao.fetchByUserId(buyerId);

        //nft info
        Nft nft = followInfo.getNft();

        //seller info
        User seller = followInfo.getUser();

        //order price
        double bid = followInfo.getBid();


        if(buyer.getAccountBalance() < bid){
            return 2;
        }else if(bid < nft.getNftPrice()){
            return 3;
        }
        else {
            orderDao.insertOrder(buyerId, seller.getUserId(),nft.getNftId(),bid);
            // deduct buyer's balance
            accountDao.updateBalanceByUserId(buyerId,bid);

            return 1;
        }
    }

    @Override
    public ArrayList<OrderInfo> findOrdersBySellerOrBuyer(int type, int userId) {
        ArrayList<OrderInfo> orderInfos = new ArrayList<>();

        if(type == 1 ){
            ArrayList<Order> bids = orderDao.selectOrdersByBuyerId(userId);
            for (Order bid : bids) {
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setOrder(bid);
                orderInfo.setNft(nftDao.selectNftByNftId(bid.getOrderNftId()));
                orderInfo.setUserDetail(userDetailDao.selectUserDetailByUserId(bid.getOrderSellerId()));
                orderInfos.add(orderInfo);
            }

        }else {
            ArrayList<Order> quotations = orderDao.selectOrdersBySellerId(userId);
            for (Order quotation : quotations) {
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setOrder(quotation);
                orderInfo.setNft(nftDao.selectNftByNftId(quotation.getOrderNftId()));
                orderInfo.setUserDetail(userDetailDao.selectUserDetailByUserId(quotation.getOrderBuyerId()));
                orderInfos.add(orderInfo);
            }

        }

        return orderInfos;
    }

    @Override
    public boolean agreeTransaction(int sellerId, int buyerId, int nftId, int orderId) {
        //1.改绑nft，nft的userid改为buyer的，version设置为0，价格改为order中的价格。
        Order order = orderDao.selectOrderByOrderId(orderId);
        Nft nft = nftDao.selectNftByNftId(nftId);
        nft.setNftUserId(buyerId);
        nft.setNftVersion(0);
        nft.setNftPrice(order.getOrderPrice());
        int flag = nftDao.tradeNft(nft);

        if(flag == 0){
            throw new BusinessException(Code.BUSINESS_ERROR,"Transaction fail");
        }

        //2.order改，status改为1，表示交易达成。
        order.setOrderStatus(1);
        int flag1 = orderDao.updateOrderStatus(order);
        if(flag1 == 0) {
            throw new BusinessException(Code.BUSINESS_ERROR,"Transaction fail");
        }

        //3.增加出售者余额
        int flag3 = accountDao.increaseBalanceByUserId(sellerId, order.getOrderPrice());
        if(flag3 == 0){
            throw new BusinessException(Code.BUSINESS_ERROR,"Transaction fail");
        }

        //4.增加日志信息，buyer的和seller都记录

        boolean flag4 = logService.logTransaction(sellerId, buyerId, nftId, order.getOrderPrice());
        if(!flag4){
            throw new BusinessException(Code.BUSINESS_ERROR,"Transaction fail");
        }

        return true;
    }

    @Override
    public void rejectTransaction(int buyerId, int orderId) {
        //订单状态改变
        Order order = orderDao.selectOrderByOrderId(orderId);
        order.setOrderStatus(2);

        if(orderDao.updateOrderStatus(order) == 0) {
            throw new BusinessException(Code.BUSINESS_ERROR,"Transaction fail");
        }

        //增加购买者余额
        if(accountDao.increaseBalanceByUserId(buyerId, order.getOrderPrice()) == 0){
            throw new BusinessException(Code.BUSINESS_ERROR,"Transaction fail");
        }

    }
}
