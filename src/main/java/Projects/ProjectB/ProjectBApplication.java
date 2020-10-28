package Projects.ProjectB;

import Projects.ProjectB.rabbitmq.Subscriber;
import Projects.ProjectB.time.ITimeDuration;
import Projects.ProjectB.time.TimeDuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.ZonedDateTime;

@SpringBootApplication
@EnableScheduling
public class ProjectBApplication {

	//private static final Logger log = LoggerFactory.getLogger(ProjectBApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProjectBApplication.class, args);
	}
/*
	@Bean
	public CommandLineRunner demo(PollRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Poll("red or blue?", "red", "blue",
					"Days:0,Hours:1,Minutes:0,Seconds:30", true, false, null));
			for (Poll poll : repository.findAll()) {
				log.info(poll.getTimeLimit());
				log.info(poll.getPollClosingDate().toString());
				String newPollClosingDate = ITimeDuration
						.timeDurationFromStringOfTimeUnits(poll.getTimeLimit())
						.futureZonedDateTimeFromTimeDuration()
						.toString();
				poll.setPollClosingDate(newPollClosingDate);
				log.info(newPollClosingDate);
				log.info("");
				log.info(poll.getPollClosingDate());
			}
			log.info(ZonedDateTime.now().toString());
		};
	}
*/
}