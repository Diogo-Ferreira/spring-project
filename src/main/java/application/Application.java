package application;

import java.util.Arrays;

import application.entities.Trip;
import application.repositories.TripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@SpringBootApplication
public class Application {



    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(TripRepository repository) {
        return args -> {
            /*repository.save(new Trip("sextrip", "Bogota"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Trip trip : repository.findAll()) {
                log.info(trip.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Trip trip = repository.findOne(1L);
            log.info("Customer found with findOne(1L):");
            log.info("--------------------------------");
            log.info(trip.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            for (Trip bauer : repository.findByName("sextrip")) {
                log.info(bauer.toString());
            }
            log.info("");
            */
        };
    }

}
