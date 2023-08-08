package danekerscode.eventservice;

import danekerscode.eventservice.utils.ELK;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }


    @Bean
    public CommandLineRunner lineRunner(
            ELK elk
    ) {
        return args -> {
            elk.run();
        };
    }

}
