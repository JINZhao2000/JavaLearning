package com.ayy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootTest
class TaskApplicationTests {
    @Autowired
    JavaMailSenderImpl mailSender;


    @Test
    void contextLoads() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setSubject("Test");
        simpleMailMessage.setTo("jinzhaofr@outlook.com");
        simpleMailMessage.setFrom("jinzhaofr@outlook.com");
        simpleMailMessage.setText("Text");

        mailSender.send(simpleMailMessage);
    }

    @Test
    void contextLoads2() throws MessagingException, URISyntaxException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true, "UTF-8");

        helper.setSubject("TestHelper");
        helper.setText("<p style='color:red'>Text</p>",true);
        URL url = TaskApplicationTests.class.getClassLoader().getResource("files/1.txt");
        assert url!=null;
        helper.addAttachment("1.txt",new File(url.toURI()));

        helper.setFrom("jinzhaofr@outlook.com");
        helper.setTo("jinzhaofr@outlook.com");

        mailSender.send(mimeMessage);
    }

}
