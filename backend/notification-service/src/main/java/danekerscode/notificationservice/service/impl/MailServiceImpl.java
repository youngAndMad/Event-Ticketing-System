package danekerscode.notificationservice.service.impl;

import danekerscode.notificationservice.service.MailService;
import danekerscode.notificationservice.utils.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void send(Notification notification) {
        System.out.println(notification);
    }

    private final Function<Notification, SimpleMailMessage> toMailMessage = notification ->
    {
        var msg = new SimpleMailMessage();

        msg.setFrom("eventticketingsystem@gmail.com");
        msg.setTo(notification.destination());
        msg.setText(notification.message());

        return msg;
    };
}
