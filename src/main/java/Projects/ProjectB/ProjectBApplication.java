package Projects.ProjectB;

import Projects.ProjectB.time.ITimeDuration;
import Projects.ProjectB.time.TimeDuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.ZonedDateTime;

@SpringBootApplication
public class ProjectBApplication {

    //private static final Logger log = LoggerFactory.getLogger(ProjectBApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProjectBApplication.class, args);
    }

}