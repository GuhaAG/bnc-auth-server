package app.writerslife.server;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("jwt.secret", dotenv.get("APPLICATION_SECRET"));
        System.setProperty("spring.mail.username", dotenv.get("SENDER_EMAIL_ADDRESS"));
        System.setProperty("spring.mail.password", dotenv.get("SENDER_EMAIL_PASSWORD"));

        SpringApplication.run(ServerApplication.class, args);
    }
}
