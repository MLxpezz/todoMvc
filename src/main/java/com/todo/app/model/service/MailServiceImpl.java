package com.todo.app.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImpl implements MailService  {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    public void sendMailConfirmAccount(String email, Long id) {
        log.info("Enviando mail a {} con id {}", email, id);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("mauricio_lopez_098@hotmail.com");
            helper.setTo(email);
            helper.setSubject("Confirmacion de cuenta");

            Context context = new Context();
            context.setVariable("confirmUrl", "http://localhost:8080/users/confirm?id=" + id);

            String htmlTemplate = springTemplateEngine.process("emailMessage", context);
            helper.setText(htmlTemplate, true);

            mailSender.send(message);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
