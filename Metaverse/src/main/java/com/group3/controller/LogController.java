package com.group3.controller;

import com.group3.common.Code;
import com.group3.common.Result;
import com.group3.dto.TransactionInfo;
import com.group3.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/logs")
public class LogController {
    @Autowired
    private LogService logService;

    /**
     * search all transactions
     * http://localhost:8080/5620/logs/transaction || get requese
     * @return ArrayList<TransactionInfo>
     */
    @GetMapping("/transaction")
    public Result searchTransaction(){
        ArrayList<TransactionInfo> result = logService.findTransaction();
        return new Result(result, Code.SELECT_OK, "Ok");
    }
}
