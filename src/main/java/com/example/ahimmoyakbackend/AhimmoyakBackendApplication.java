package com.example.ahimmoyakbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AhimmoyakBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AhimmoyakBackendApplication.class, args);
    }

}
