package com.jack.springboot.controller;


import com.jack.springboot.email.SimpleMailSend;
import com.jack.springboot.email.TemplateMailSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    SimpleMailSend simpleMailSend;
    @Autowired
    TemplateMailSend templateMailSend;
    @RequestMapping("/index")
    public String index() throws Exception{
        System.out.println("1111111111113333123123");
        simpleMailSend.simpleSend("6993476@qq.com","test","hello mail");

        simpleMailSend.sendWithHtml("69934765@qq.com","test","hello mail");

        templateMailSend.sendWithThymeleafHtmlTemplate("69934765@qq.com","test1","mail/html/mail-hello","html","logo.png");

        templateMailSend.sendWithThymeleafTextTemplate("69934765@qq.com","test2","mail/text/mail-hello","txt");
        return "index" ;
    }
}