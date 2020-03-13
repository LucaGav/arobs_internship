package com.arobs.internship.library.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("mailService")
public class EmailServiceImpl {

    @Autowired
    public JavaMailSender mailSender;

    public void sendEmail() {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject("Test");
            mimeMessageHelper.setFrom("luca971000gmail.com");
            mimeMessageHelper.setTo("gavril.luca7@gmail.com");
            mimeMessageHelper.setText("Corona");

            mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

