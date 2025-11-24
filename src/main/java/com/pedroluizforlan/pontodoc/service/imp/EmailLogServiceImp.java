package com.pedroluizforlan.pontodoc.service.imp;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import com.pedroluizforlan.pontodoc.model.EmailLog;
@Service
public class EmailLogServiceImp {
    private final JavaMailSender mailSender;

    private EmailLogServiceImp(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailLog email) {
    var message = new SimpleMailMessage();
        message.setFrom("noreply@pontodoc.com");
        message.setTo(email.getCollaborator().getUser().getEmail());
        message.setSubject(email.getEmailSubject());
        message.setText(email.getEmailBody());
        mailSender.send(message);
    }

}
