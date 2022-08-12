package com.group3.service.impl;

import com.group3.service.MailService;
import com.group3.utils.CreateMailCode;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    private String from = "day.dong99@yahoo.com";

    private String subject = "Hello, this is your mail verification code";

    @Resource
    private FreeMarkerConfigurer configurer;

    @Resource
    private JavaMailSender javaMailSender;

    @Autowired
    private CreateMailCode createMailCode;

    @Override
    //put mail code to cache, 300s expired
    @CachePut(value = "MailCodeSpace", key = "#mailAddress")
    public Boolean sentMailCode(String mailAddress) {
        try {

            //data in mail
            Map<String,String> model = new HashMap<String,String>();
            model.put("mailCode", createMailCode.CreateMailCode());


            Template template = configurer.getConfiguration().getTemplate("mail.ftl");

            //get the context of mail html
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(subject);

            helper.setFrom(from + "(group3)");

            helper.setTo(mailAddress);

//            helper.addAttachment("Img4_2x.jpg", new File(this.getClass().getResource("Img4_2x.jpg").getPath()));
//            helper.addAttachment("footer.png", new File(this.getClass().getResource("footer.png").getPath()));
//            helper.addAttachment("Img4_2x.jpg", new ClassPathResource("Img4_2x.jpg"));
//            helper.addAttachment("footer.png", new ClassPathResource("footer.png"));

            helper.setText(content,true);

            javaMailSender.send(mimeMessage);


        } catch (Exception e) {

            return false;
        }

        return true;
    }
}
