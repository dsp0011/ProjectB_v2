package Projects.ProjectB.rabbitmq;

import Projects.ProjectB.Poll;
import Projects.ProjectB.ProjectBApplication;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Subscriber {

    private static final Logger log = LoggerFactory.getLogger(Subscriber.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @RabbitListener(queues = "dweet_queue")
    public void subscribeDweet(final Message message) {
        /**
         Send message to DWEET
        */

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