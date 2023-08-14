package danekerscode.notificationservice.listener;

import danekerscode.notificationservice.service.MailService;
import danekerscode.notificationservice.utils.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultKafkaListener {

    private final MailService mailService;

    @KafkaListener(topics = "notification_topic", groupId = "groupId")
    public void mailListener(Notification notification) {
        log.info("new {}", notification);
        mailService.send(notification);
    }

}
