package danekerscode;

import danekerscode.utils.Notification;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(
            KafkaTemplate<String, Notification> br
    ) {
        return args -> {
            br.send(
                    "notification_topic",
                    new Notification("welcome to event ticket system", "emao")
            );
        };
    }
}
