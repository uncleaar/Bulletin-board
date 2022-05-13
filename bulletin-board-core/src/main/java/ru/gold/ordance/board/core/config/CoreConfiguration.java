package ru.gold.ordance.board.core.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("ru.gold.ordance.board.core.persistence")
@EntityScan("ru.gold.ordance.board.core.entity")
@ComponentScan(value = {"ru.gold.ordance.board.core"})
public class CoreConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{ "localhost" });
        ds.setDatabaseName("board");
        ds.setUser("postgres");
        ds.setPassword("postgres");

        return ds;
    }
}
