package danekerscode.notificationservice.listener;

import danekerscode.notificationservice.utils.Notification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class DefaultKafkaListener {

    @KafkaListener(topics = "notification_topic" , groupId = "groupId")
    public void mailListener(Notification notification) {
        System.out.println(notification);
    }

}
