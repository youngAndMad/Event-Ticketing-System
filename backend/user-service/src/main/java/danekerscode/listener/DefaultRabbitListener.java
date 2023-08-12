package danekerscode.listener;

import danekerscode.service.UserService;
import danekerscode.utils.Notification;
import danekerscode.utils.TicketEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static danekerscode.utils.AppConstants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultRabbitListener {

    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;

    @RabbitListener(queues = TICKET_QUEUE)
    void listen(
            TicketEvent ticketEvent
    ) {
        log.info("new ticket event {}" , ticketEvent);
        var user = userService.findById(ticketEvent.userId());
        rabbitTemplate.convertAndSend(
                EMAIL_EXCHANGE,
                EMAIL_ROUTING_KEY,
                new Notification(
                        "Ticket code: %s , status: %s".formatted(ticketEvent.code(), ticketEvent.status()),
                        user.getEmail()
                )
        );
    }

}
