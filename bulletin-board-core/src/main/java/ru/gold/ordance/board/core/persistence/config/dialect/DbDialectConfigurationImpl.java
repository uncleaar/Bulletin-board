package ru.gold.ordance.board.core.persistence.config.dialect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbDialectConfigurationImpl implements DbDialectConfiguration {
    private final DbDialect dbDialect;

    public DbDialectConfigurationImpl(@Value("${persistence.dialect:}") DbDialect dbDialect) {
        this.dbDialect = dbDialect;
    }

    @Override
    public DbDialect dbDialect() {
        return dbDialect;
    }
}
