package danekerscode.listener;

import danekerscode.service.UserService;
import danekerscode.utils.Notification;
import danekerscode.utils.TicketEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultKafkaListener {

    private final UserService userService;
    private final KafkaTemplate<String,Notification> kafkaTemplate;

    @KafkaListener(topics = "ticket_topic" , groupId = "groupId")
    void listen(
            TicketEvent ticketEvent
    ) {
        log.info("new ticket event {}" , ticketEvent);
        var user = userService.findById(ticketEvent.userId());
        kafkaTemplate.send(
                "email_topic",
                new Notification(
                        "Ticket code: %s , status: %s".formatted(ticketEvent.code(), ticketEvent.status()),
                        user.getEmail()
                )
        );
    }

}
