package danekerscode.notificationservice.listener;

import danekerscode.notificationservice.utils.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static danekerscode.notificationservice.utils.AppConstants.EMAIL_QUEUE;

@Component
public class DefaultRabbitListener {

    @RabbitListener(queues = EMAIL_QUEUE)
    public void mailListener(Notification notification) {
        System.out.println(notification);
    }

}
