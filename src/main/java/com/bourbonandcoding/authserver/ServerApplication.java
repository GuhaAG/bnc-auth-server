package com.bourbonandcoding.authserver;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("jwt.secret", dotenv.get("APPLICATION_SECRET"));
        System.setProperty("spring.mail.username", dotenv.get("SENDER_EMAIL_ADDRESS"));
        System.setProperty("spring.mail.password", dotenv.get("SENDER_EMAIL_PASSWORD"));
        System.setProperty("spring.datasource.url", dotenv.get("USER_DB_URL"));
        System.setProperty("spring.datasource.username", dotenv.get("USER_DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("USER_DB_PASSWORD"));

        SpringApplication.run(ServerApplication.class, args);
    }
}
