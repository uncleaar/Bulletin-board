package ru.gold.ordance.board.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import ru.gold.ordance.board.core.persistence.config.PersistenceConfiguration;

@Configuration
@Import(PersistenceConfiguration.class)
@ComponentScan(value = {"ru.gold.ordance.board.core"})
public class CoreConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }
}
