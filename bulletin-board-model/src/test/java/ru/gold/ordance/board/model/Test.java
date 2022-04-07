package ru.gold.ordance.board.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.gold.ordance.board.model.spring.EntityConfiguration;

@SpringBootApplication
@Import(EntityConfiguration.class)
public class Test {
    public static void main(String[] args) {
        SpringApplication.run(Test.class, args);
    }
}
