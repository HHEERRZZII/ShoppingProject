package com.herzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MessageService {
    public static void main(String[] args) {
        SpringApplication.run(MessageService.class, args);
    }
}
