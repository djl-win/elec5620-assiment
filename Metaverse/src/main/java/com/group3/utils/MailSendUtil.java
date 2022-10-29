package com.group3.utils;

import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailSendUtil{

    private String from = "day.dong99@yahoo.com";

    private String subject = "Hello, this is your mail verification code";

    @Resource
    private FreeMarkerConfigurer configurer;

    @Resource
    private JavaMailSender javaMailSender;

    @Autowired
    private CreateMailCode createMailCode;



    //put mail code to cache, 300s expired
    //This method can not be trim, because the aop has not been intercepted, it is stored in the cache,
    // to achieve the words, intercept the last controller, it is not modified first
    @CachePut(value = "MailCodeSpace", key = "#mailAddress")
    public String sentMailCode(String mailAddress) {
        String mailCodeInCache = null;
        try {
            mailCodeInCache = createMailCode.CreateMailCode();

            //data in mail
            Map<String,String> model = new HashMap<String,String>();
            model.put("mailCode", mailCodeInCache);

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

            return "false";
        }

        return mailCodeInCache;
    }


    public boolean checkMailCode(String mailAddress, String mailCode) {
//        System.out.println(mailCode);
//
//        System.out.println(createMailCode.getMailVerificationCodeFromCache(mailAddress));

        //get code from cache
        String mailVerificationCodeFromCache = createMailCode.getMailVerificationCodeFromCache(mailAddress);
        //check whether the input code = or not code from cache
        return mailCode.equals(mailVerificationCodeFromCache);
    }
}
