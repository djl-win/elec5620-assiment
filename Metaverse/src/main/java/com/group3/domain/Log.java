package com.group3.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Log {

    private int logId;

    private String logPubKeyUserA;

    private String logPubKeyUserB;

    private int logType;

    private int logNftId;

    private double logPrice;

    private Date logDate;

    private String logDescription;

    private int logStatus;
}
