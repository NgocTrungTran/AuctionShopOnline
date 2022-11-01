package com.aso.controller;

import com.aso.service.gmail.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Controller
public class HtmlEmailExampleController {
    @Autowired
    public JavaMailSender emailSender;

    @ResponseBody
    @RequestMapping("/sendHtmlEmail")
    public String sendHtmlEmail() throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String htmlMsg = "<h3>AUCTIONS SHOP ONLINE</h3>"
                +"<p>Chào mừng bạn đến với Auctions Shop</p>";

        message.setContent(htmlMsg, "text/html");

        helper.setTo( MyConstants.FRIEND_EMAIL);

        helper.setSubject("Test send HTML email");


        this.emailSender.send(message);

        return "Email Sent!";
    }
}
