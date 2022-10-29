package com.group3.dao;

import com.group3.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface OrderDao {

    ArrayList<Order> selectAllOrder();

    /**
     * new bid to order
     * @param buyerId buyer id
     * @param sellerId seller id
     * @param nftId nft id
     * @param bid price
     * @return 1 or 0
     */
    int insertOrder(@Param("buyerid")int buyerId, @Param("sellerid")int sellerId, @Param("nftid")int nftId, @Param("bid")double bid);

    /**
     * selectOrdersByBuyerId
     * @param userId userid
     * @return order list
     */
    ArrayList<Order> selectOrdersByBuyerId(@Param("userid")int userId);

    /**
     * selectOrdersBySellerId
     * @param userId userid
     * @return order list
     */
    ArrayList<Order> selectOrdersBySellerId(@Param("userid")int userId);

    /**
     * select order by order id
     * @param orderId order id
     * @return order
     */
    Order selectOrderByOrderId(@Param("orderid")int orderId);

    /**
     * update order
     * @param order order info
     * @return ok or not
     */
    int updateOrderStatus(@Param("order")Order order);
}
