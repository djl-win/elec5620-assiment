package com.group3.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = -1, rollbackFor = Exception.class)
public interface MailService {

    String sentMailCode(String mailAddress);

    boolean checkMailCode(String mailAddress, String mailCode);
}
