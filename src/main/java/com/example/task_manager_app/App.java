package com.example.task_manager_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
                Dotenv dotenv = Dotenv.load();

                System.setProperty("DB_URL", dotenv.get("DB_URL"));
                System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
                System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

                System.setProperty("REDIS_URL", dotenv.get("REDIS_URL"));
                System.setProperty("REDIS_PORT", dotenv.get("REDIS_PORT"));

                System.setProperty("OTP_LENGTH", dotenv.get("OTP_LENGTH"));
                System.setProperty("OTP_TTL", dotenv.get("OTP_TTL"));
                System.setProperty("MAX_ATTEMPT_LENGTH", dotenv.get("MAX_ATTEMPT_LENGTH"));

                System.setProperty("MAIL_HOST", dotenv.get("MAIL_HOST"));
                System.setProperty("MAIL_PROTOCOL", dotenv.get("MAIL_PROTOCOL"));
                System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
                System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
                System.setProperty("MAIL_SMTP_AUTH", dotenv.get("MAIL_SMTP_AUTH"));
                System.setProperty("MAIL_PORT", dotenv.get("MAIL_PORT", "587"));
                System.setProperty("MAIL_STARTTLS", dotenv.get("MAIL_STARTTLS", "true"));
//        System.setProperty("MAIL_SMTP_SSL", dotenv.get("MAIL_SMTP_SSL"));
		SpringApplication.run(App.class, args);

        }

}
