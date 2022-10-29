package com.group3.controller;

import com.alibaba.fastjson.JSONObject;
import com.group3.common.Code;
import com.group3.common.Result;
import com.group3.domain.User;
import com.group3.dto.FollowInfo;
import com.group3.dto.OrderInfo;
import com.group3.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * post make a bid 接口地址：http://localhost:8080/5620/orders/bid post请求
     * @param followInfo 获取该用户当前点击的的nft信息,里面有竞价信息
     * @return 返回成功与否
     */
    @PostMapping("/bid")
    public Result bidNft(@RequestBody FollowInfo followInfo, HttpServletRequest request){
        //获取用户id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int buyerId = user.getUserId();

        System.out.println(followInfo);
        //1.检查用户是否有足够金额 Insufficient balance 2.扣除相应金额 3.新增order记录 ok
        int flag = orderService.newOrder(followInfo, buyerId);

        if(flag == 1){
            return new Result(null, Code.INSERT_OK,"Please check the order");
        } else if (flag == 2) {
            return new Result(null, Code.INSERT_FAIL,"Sorry, you don't have enough ATX");
        } else if (flag == 3) {
            return new Result(null, Code.INSERT_FAIL,"Sorry, Your bid needs to be greater than the current asking price");
        } else {
            return new Result(null, Code.INSERT_FAIL,"Sorry, try again");
        }
    }

    /**
     * search this user order{1 means bid order, 2 means quotation order}
     * 接口地址：http://localhost:8080/5620/orders/searchOrderInfo/# get请求
     * @param request userid
     * @return yes or no
     */
    @GetMapping("/searchOrderInfo/{type}")
    public Result searchOrderInfo(@PathVariable("type")int type,HttpServletRequest request) {
        //获取用户id
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        //type = 1 bid , type = 2 quotaion
        if(type == 1 || type ==2){
            int userId = user.getUserId();
            ArrayList<OrderInfo> orders = orderService.findOrdersBySellerOrBuyer(type,userId);
            return new Result(orders, Code.SELECT_OK, "OK");
        }else{
            return new Result(null, Code.SELECT_FAIL,"Fail to search");
        }
    }

    /**
     * post agree transaction
     * address: http://localhost:8080/5620/orders/agree
     * @param input buyer id , nft id, order id
     * @param request seller id
     * @return ok or not
     */
    @PostMapping("/agree")
    public Result agreeOrder(@RequestBody String input, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int sellerId = user.getUserId();

        JSONObject jsonObject = JSONObject.parseObject(input);
        int orderId = jsonObject.getInteger("orderId");
        int buyerId = jsonObject.getInteger("userDetailUserId");
        int nftId = jsonObject.getInteger("nftId");

        orderService.agreeTransaction(sellerId, buyerId, nftId, orderId);

        return new Result(null, Code.UPDATE_OK,"Congratulations, the deal is done!");

    }

    /**
     * post agree transaction
     * address: http://localhost:8080/5620/orders/agree
     * @param input buyer id, order id
     * @return ok or not
     */
    @PostMapping("/reject")
    public Result rejectOrder(@RequestBody String input){
        JSONObject jsonObject = JSONObject.parseObject(input);
        int orderId = jsonObject.getInteger("orderId");
        int buyerId = jsonObject.getInteger("userDetailUserId");
        orderService.rejectTransaction(buyerId, orderId);

        return new Result(null, Code.UPDATE_OK,"You have rejected this deal successfully");

    }

}
