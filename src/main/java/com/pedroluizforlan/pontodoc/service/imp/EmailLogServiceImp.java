package com.pedroluizforlan.pontodoc.service.imp;

import com.pedroluizforlan.pontodoc.repository.EmailLogRepository;
import com.pedroluizforlan.pontodoc.util.EmailLogUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.EmailLog;
import org.thymeleaf.TemplateEngine;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EmailLogServiceImp {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final EmailLogRepository emailLogRepository;


    @Async
    public void sendHtmlEmail(Collaborator collaborator, EmailLog.EmailType type , String subject, String templateName, Map<String, Object> variables) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            Context context = new Context();
            context.setVariables(variables);
            String html = templateEngine.process("email/" + templateName, context);

            helper.setTo("pedro.forlan2000@edu.unifil.br");//pedro.forlan2000@edu.unifil.br
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom("pedro.forlan2000@edu.unifil.br");

            mailSender.send(message);

            System.out.println(type);
            var email = EmailLogUtils.createEmailLog(collaborator,type,subject,templateName);

            emailLogRepository.save(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar e-mail", e);
        }
    }
}


