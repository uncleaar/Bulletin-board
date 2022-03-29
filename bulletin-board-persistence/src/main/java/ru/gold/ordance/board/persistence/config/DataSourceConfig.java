package ru.gold.ordance.board.persistence.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(basePackages = "ru.gold.ordance.board.persistence.repository.*")
public class DataSourceConfig {
    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{"localhost"});
        ds.setDatabaseName("board");
        ds.setUser("postgres");
        ds.setPassword("postgres");

        return ds;
    }
}
