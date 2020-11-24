package Projects.ProjectB.messaging;

import Projects.ProjectB.entities.Poll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.messaging.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.client.RestTemplate;

public class Subscriber {

    private static final Logger log = LoggerFactory.getLogger(Subscriber.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @RabbitListener(queues = "dweet_queue")
    public void subscribeDweet(final Message message) {
        log.info("Dweet message received");

        Poll poll = (Poll) message.getPayload();
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl  = "https://dweet.io/dweet/for/dat250-group6-polls";
        HttpEntity<Poll> request = new HttpEntity<>(poll);
        restTemplate.postForObject(fooResourceUrl, request, Poll.class);
    }

    @RabbitListener(queues = "results_queue")
    public void subscribeResults(final Message message) {
        log.info("Result message received");

        Poll poll = (Poll) message.getPayload();
        mongoTemplate.insert(poll, "poll");
    }
}