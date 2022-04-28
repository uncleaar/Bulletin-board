package ru.gold.ordance.board.model.entity.spring;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "ru.gold.ordance.board.model.entity")
public class EntityConfiguration {
}
