package com.group3.controller;

import com.group3.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nfts")
public class NftController {


    @PostMapping("/createNft")
    public Result createNewNft(){
        System.out.println("aaa");
        return null;
    }
}
