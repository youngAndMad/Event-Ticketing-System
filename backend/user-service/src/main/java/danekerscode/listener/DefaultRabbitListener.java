package danekerscode.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static danekerscode.utils.AppConstants.TICKET_QUEUE;

@Component
public class DefaultRabbitListener {

    @RabbitListener(queues = TICKET_QUEUE)
    void listen(){

    }

}
