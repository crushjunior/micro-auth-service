package ru.charushnikov.microauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroAuthServiceApplication.class, args);
    }

}
