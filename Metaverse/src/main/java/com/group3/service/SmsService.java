package com.group3.service;

import com.group3.domain.SmsMessage;
import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = -1, rollbackFor = Exception.class)
public interface SmsService {

    String sentCode(String tel);

    boolean checkCode(SmsMessage smsMessage);
}
