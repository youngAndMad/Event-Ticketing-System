package danekerscode.eventservice.scheduled;

import danekerscode.eventservice.repository.EventRepository;
import danekerscode.eventservice.utils.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
@EnableAsync
@RequiredArgsConstructor
public class EventSending {

    private final EventRepository eventRepository;

    @Async
    @Scheduled(cron = "0 0 0 * * ?")
    public void delete(){
        eventRepository.deleteAll(eventRepository.findAllByTimeBefore(LocalDate.now()));
    }

    private <T> T getRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(list.size());

        return list.get(randomIndex);
    }

}
