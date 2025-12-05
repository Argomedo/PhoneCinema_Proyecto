package com.ejemplo.serviciofeedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServicioFeedbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioFeedbackApplication.class, args);
    }

}
