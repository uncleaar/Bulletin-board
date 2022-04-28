package ru.gold.ordance.board.persistence.spring;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.gold.ordance.board.model.entity.spring.EntityConfiguration;

import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(basePackages = "ru.gold.ordance.board.persistence.repository")
@ComponentScan(basePackages = "ru.gold.ordance.board.persistence.utils")
@Import(EntityConfiguration.class)
public class DataSourceConfiguration {

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
