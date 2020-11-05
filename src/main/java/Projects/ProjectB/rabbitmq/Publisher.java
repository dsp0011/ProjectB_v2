package Projects.ProjectB.rabbitmq;

import Projects.ProjectB.Poll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class Publisher {

    private static final Logger log = LoggerFactory.getLogger(Publisher.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Poll poll, String routing) {
        rabbitTemplate.convertAndSend("pollExchange", routing, poll);
        //rabbitTemplate.convertAndSend("pollExchange", routing, "Hello World!");
        log.info("Message sent");
    }
}