package danekerscode.notificationservice.service.impl;

import danekerscode.notificationservice.service.MailService;
import danekerscode.notificationservice.utils.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

//    private final JavaMailSender mailSender;

    @Override
    public void send(Notification notification) {
        System.out.println(notification);
    }
}
