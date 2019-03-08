package com.jack.springboot.email;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

/**
 * @author duliu
 */
@Component
public class SimpleMailSend {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender; //发送地址

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 发送给单一的用户
     * @param toMail
     * @param content
     */
    public void simpleSend(String toMail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toMail);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(sender); //确保发送邮件地址与认证地址，如果不一致， 一些邮箱服务器会退信

        this.mailSender.send(message);
    }

    /**
     * 发送 给多个地址，并且抄送多人，同时隐身抄送给多个人
     * @param toMails 收件人地址
     * @param ccMails 抄送人地址
     * @param bccMails 隐形抄送地址 不会出现在地址栏
     * @param subject 标题
     * @param content 内容
     */
    public void simpleSend(String [] toMails, String [] ccMails, String [] bccMails, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toMails);
        message.setCc(ccMails);
        message.setBcc(bccMails);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(sender); //确保发送邮件地址与认证地址，如果不一致， 一些邮箱服务器会退信

        this.mailSender.send(message);
    }

    /**
     * 使用 MimeMessagePreparator  回掉接口來发送html 同时 也可以使用 MimeMessageHelper
     * @param toMail
     * @param subject
     * @param content html
     */
    public void sendWithHtml(String toMail, String subject, String content) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
                mimeMessage.setSubject(subject);
                mimeMessage.setFrom(new InternetAddress(sender));
                mimeMessage.setText(content, StandardCharsets.UTF_8.name(), "html");
            }
        };

        this.mailSender.send(preparator);
    }


    /**
     * 发送带有附件的邮件
     * @param toMail
     * @param subject
     * @param content
     * @param files
     * @throws MessagingException
     */
    public void sendWithAttachemnt(String toMail, String subject, String content, List<String> files) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toMail);
        helper.setSubject(subject);
        helper.setText(content); //支持发送html setText(String text, boolean html)
        helper.setFrom(sender);

        for (int i = 0; i < files.size(); i++) {

            Resource resource =  resourceLoader.getResource(files.get(i));

            helper.addAttachment(resource.getFilename() , resource);
        }

        this.mailSender.send(message);
    }

}
