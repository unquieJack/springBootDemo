package com.jack.springboot.email;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author duliu
 *
 */
@Component
public class TemplateMailSend {

    public Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TemplateEngine textEmailTemplateEngine;

    @Autowired
    private TemplateEngine htmlEmailTemplateEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     *  使用Html模板来生产发送消息
     * @param toMail
     * @param subject
     * @param file
     * @throws MessagingException
     */
    public void sendWithThymeleafHtmlTemplate(String toMail, String subject, String file, String recipientName, String imageResourceName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        helper.setTo(toMail);
        helper.setSubject(subject);
        helper.setFrom(sender);

        //设置方言环境
        final Context ctx = new Context(Locale.SIMPLIFIED_CHINESE);

        ctx.setVariable("name", recipientName);
        ctx.setVariable("imageResourceName", imageResourceName);

        String htmlContent = this.htmlEmailTemplateEngine.process(file, ctx);

        Resource resource =  resourceLoader.getResource("classpath:static/logo.png");

        helper.setText(htmlContent, true);

        helper.addInline(imageResourceName, resource);

        log.info(htmlContent);

        this.mailSender.send(message);
    }

    /**
     * 使用Text模板来生产发送消息
     * @param toMail
     * @param subject
     * @param file
     * @param recipientName
     * @throws MessagingException
     */
    public void sendWithThymeleafTextTemplate(String toMail, String subject, String file, String recipientName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        helper.setTo(toMail);
        helper.setSubject(subject);
        helper.setFrom(sender);

        //设置方言环境
        final Context ctx = new Context(Locale.SIMPLIFIED_CHINESE);

        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("吃", "吃", "吃"));

        String txtContent = this.textEmailTemplateEngine.process(file, ctx);

        helper.setText(txtContent);

        log.info(txtContent);

        this.mailSender.send(message);
    }
}
