package Projects.ProjectB.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("pollExchange");
    }

    private static class ReceiverConfig {

        @Bean
        public Queue resultsQueue() {
            return new Queue("results_queue");
        }

        @Bean
        public Queue dweetQueue() {
            return new Queue("dweet_queue");
        }

        @Bean
        public Binding resultsBinding(TopicExchange topicExchange, Queue resultsQueue) {
            return BindingBuilder.bind(resultsQueue).to(topicExchange).with("poll.close");
        }

        @Bean
        public Binding dweetBinding(TopicExchange topicExchange, Queue dweetQueue) {
            return BindingBuilder.bind(dweetQueue).to(topicExchange).with("poll.#");
        }

        @Bean
        public Subscriber subscriber() {
            return new Subscriber();
        }
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Publisher publisher() {
        return new Publisher();
    }

}
