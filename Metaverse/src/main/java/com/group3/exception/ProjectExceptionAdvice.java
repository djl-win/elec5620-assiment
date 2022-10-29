package com.group3.exception;

import com.group3.common.Code;
import com.group3.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {
    /**
     * catch the unknown exception
     */
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex){
        //record the log
        System.out.println("unknown exception: " + ex.getMessage());
        ex.printStackTrace();
        //operations

        //return info to web developer
        return new Result(null, Code.UNKNOWN_ERROR,"unknown exception");

    }

    /**
     * catch the business exception
     */
    @ExceptionHandler(BusinessException.class)
    public Result businessException(BusinessException ex){
        //record the log
        System.out.println("business exception: " + ex.getMessage());
        //operations

        //return info to web developer
        return new Result(null, Code.BUSINESS_ERROR,ex.getMessage());
    }

    /**
     * catch the System exception
     */
    @ExceptionHandler(SystemException.class)
    public Result systemException(SystemException ex){
        //record the log
        System.out.println("System exception: " + ex.getMessage());
        //operations

        //return info to web developer
        return new Result(null, Code.SYSTEM_ERROR,"system exception");
    }


}
