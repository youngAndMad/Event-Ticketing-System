package danekerscode.eventservice.config;

import danekerscode.eventservice.utils.Notification;
import danekerscode.eventservice.utils.NotificationType;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static danekerscode.eventservice.utils.AppConstants.*;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(EMAIL_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public CommandLineRunner runner(
            @Qualifier("template") RabbitTemplate rabbitBroker
    ) {
        return args -> {
            for (int i = 0; i < 100; i++) {
                rabbitBroker.convertAndSend(EMAIL_EXCHANGE, EMAIL_ROUTING_KEY, new Notification(
                        "test message: " + i,
                        "test destination: " + i,
                        NotificationType.EMAIL
                ));
                System.out.println("success: " + i);
            }
        };
    }


}
