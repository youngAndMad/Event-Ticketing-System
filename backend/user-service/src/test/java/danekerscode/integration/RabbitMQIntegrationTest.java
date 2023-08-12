package danekerscode.integration;

import org.junit.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
public class RabbitMQIntegrationTest {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Container
    public static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer()
            .withExposedPorts(5672);


    @DynamicPropertySource
    static void configureRabbitMQ(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getContainerIpAddress);
        registry.add("spring.rabbitmq.port", rabbitMQContainer::getFirstMappedPort);
    }


    @Test // FIXME: 8/12/2023
    public void testRabbitMQConnection() {
        assertTrue(true);
        assertTrue(connectionFactory.createConnection().isOpen());
    }

}
