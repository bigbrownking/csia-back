package org.agro.agrohack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "org.agro.agrohack")
public class AgrohackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgrohackApplication.class, args);
    }

}
