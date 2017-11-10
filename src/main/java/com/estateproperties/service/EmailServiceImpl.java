package com.estateproperties.service;

import com.estateproperties.model.Apartment;
import com.estateproperties.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private JavaMailSender mailSender;

    @Value("${admin.email}")
    private String adminEmail;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendEmail(SimpleMailMessage email) {

        LOG.info(String.format("Mail sending: [%s], [%s]", email.getText(), email.getTo()));
        mailSender.send(email);
        LOG.info(String.format("Mail sent: [%s], [%s]", email.getText(), email.getTo()));
    }

    public SimpleMailMessage getApplicationConfirmEmailForUser(User user) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Application Confirmation");
        message.setText("Hello, \n" + user.getName() +
                "You application confirmed. \n" +
                        "Our managers will contact you.");
        message.setFrom(adminEmail);

        return message;
    }

    public SimpleMailMessage getApplicationConfirmEmailForAdmin(User user, Apartment apartment) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("New Application!");
        message.setText(String.format("Hello! \n New application from user:\n" +
                "Name: %s\n" +
                "Last name: %s\n" +
                "Email: %s\n" +
                "Apartment name: %s\n" +
                "Apartment Id: %s",
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                apartment.getName(),
                apartment.getId()));
        message.setFrom("noreplay@estateproperties.com");

        return message;
    }
}
