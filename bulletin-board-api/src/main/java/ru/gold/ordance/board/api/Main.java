package ru.gold.ordance.board.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.gold.ordance.board.api.security.WebSecurityConfig;

@SpringBootApplication
@Import(WebSecurityConfig.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
