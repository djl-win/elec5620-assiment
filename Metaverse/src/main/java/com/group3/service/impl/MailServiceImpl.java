package com.group3.service.impl;

import com.group3.service.MailService;
import com.group3.utils.CreateMailCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MailServiceImpl implements MailService {

    private String from = "day.dong99@yahoo.com";

    private String subject = "Your mail verification code";

    @Resource
    private JavaMailSender javaMailSender;

    @Autowired
    private CreateMailCode createMailCode;

    @Override
    //put mail code to cache, 300s expired
    @CachePut(value = "MailCodeSpace", key = "#mailAddress")
    public Boolean sentMailCode(String mailAddress) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(from + "(group3)");

            simpleMailMessage.setTo(mailAddress);

            simpleMailMessage.setSubject(subject);

            simpleMailMessage.setText("You code is "+ createMailCode.CreateMailCode());

            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
