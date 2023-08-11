package danekerscode.ticketservice;

import danekerscode.ticketservice.client.UserClient;
import feign.FeignException;
import org.apache.tomcat.util.modeler.FeatureInfo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class TicketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner r(
            UserClient client
    ){
        return args -> {
            try {
                System.out.println(client.findById(1L));
            }catch (FeignException.Unauthorized e){
                System.out.println(e.getMessage());
            }
        };
    }


}
