package ru.gold.ordance.board.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.gold.ordance.board.core.config.CoreConfiguration;

@SpringBootApplication
@Import(CoreConfiguration.class)
public class Test {
    public static void main(String[] args) {
        SpringApplication.run(Test.class, args);
    }
}
