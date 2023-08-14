package danekerscode.notificationservice;

import danekerscode.notificationservice.service.MailService;
import danekerscode.notificationservice.utils.Notification;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.beans.BeanProperty;

@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(
            MailService mailService
    ){
        return args -> {
            System.out.println("sending");
            mailService.send(new Notification(
                    "test@gmail.com"
                    ,"love you"));
        };
    }


}
