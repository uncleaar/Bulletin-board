package ru.gold.ordance.board.core.persistence.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.gold.ordance.board.core.persistence.config.connection.DbConnectionConfiguration;
import ru.gold.ordance.board.core.persistence.config.connection.H2ConnectionConfiguration;
import ru.gold.ordance.board.core.persistence.config.connection.PostgresConnectionConfiguration;
import ru.gold.ordance.board.core.persistence.config.dialect.DbDialect;
import ru.gold.ordance.board.core.persistence.config.dialect.DbDialectConfiguration;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("ru.gold.ordance.board.core.persistence")
@EntityScan("ru.gold.ordance.board.core.entity")
public class PersistenceConfiguration {
    private final DbDialect dbDialect;

    public PersistenceConfiguration(DbDialectConfiguration dbDialectConfig) {
        this.dbDialect = dbDialectConfig.dbDialect();
    }

    @Bean("dbConnection")
    @Profile("!test")
    public DbConnectionConfiguration connectionConfig(PostgresConnectionConfiguration ps, H2ConnectionConfiguration h2) {
        switch (dbDialect) {
            case POSTGRES:
                return ps;
            case H2:
                return h2;
            default:
                throw new IllegalStateException("Unexpected value dbDialect: " + dbDialect);
        }
    }

    @Bean
    @Profile("!test")
    public DataSource dataSource(@Qualifier("dbConnection") DbConnectionConfiguration dbConfig) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(dbConfig.url());
        hikariDataSource.setUsername(dbConfig.username());
        hikariDataSource.setPassword(dbConfig.password());

        return hikariDataSource;
    }
}
