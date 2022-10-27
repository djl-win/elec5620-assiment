package com.group3.controller;

import com.group3.common.Code;
import com.group3.common.Result;
import com.group3.dto.FollowInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/orders")
public class OrderController {

    /**
     * post make a bid 接口地址：http://localhost:8080/5620/orders/bid post请求
     * @param followInfo 获取该用户当前点击的的nft信息,里面有竞价信息
     * @return 返回成功与否
     */
    @PostMapping("/bid")
    public Result bidNft(@RequestBody FollowInfo followInfo, HttpServletRequest request){


        System.out.println(followInfo);
        //1.检查用户是否有足够金额 2.扣除相应金额 3.新增order记录

        return new Result(null, Code.INSERT_OK,"Please check the order");


    }
}
