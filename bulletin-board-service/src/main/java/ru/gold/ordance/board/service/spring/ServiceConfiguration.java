package ru.gold.ordance.board.service.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import ru.gold.ordance.board.persistence.spring.DataSourceConfiguration;

@Configuration
@ComponentScan(basePackages = "ru.gold.ordance.board.service.base")
@Import(DataSourceConfiguration.class)
public class ServiceConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }
}
