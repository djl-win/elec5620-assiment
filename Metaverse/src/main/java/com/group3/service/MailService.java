package com.group3.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = -1, rollbackFor = Exception.class)
public interface MailService {

    Boolean sentMailCode(String mailAddress);
}
