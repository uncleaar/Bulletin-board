package ru.gold.ordance.board.web.service.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.gold.ordance.board.service.spring.ServiceConfiguration;

@Configuration
@ComponentScan(basePackages = {"ru.gold.ordance.board.web.service.complex",
        "ru.gold.ordance.board.web.service.base",
        "ru.gold.ordance.board.web.service.auth"})
@Import(ServiceConfiguration.class)
public class WebServiceConfiguration {
}
