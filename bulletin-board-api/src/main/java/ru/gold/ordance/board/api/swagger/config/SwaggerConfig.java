package ru.gold.ordance.board.api.swagger.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi addressOpenApi(@Value("${address.info.title:}") String title,
                                         @Value("${common.description:}") String description,
                                         @Value("${address.group-name:}") String groupName,
                                         @Value("${address.paths:}") String [] paths) {
        return controller(title, description, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi advertisementOpenApi(@Value("${advertisement.info.title:}") String title,
                                               @Value("${common.description:}") String description,
                                               @Value("${advertisement.group-name:}") String groupName,
                                               @Value("${advertisement.paths:}") String [] paths) {
        return controller(title, description, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi categoryOpenApi(@Value("${category.info.title:}") String title,
                                          @Value("${common.description:}") String description,
                                          @Value("${category.group-name:}") String groupName,
                                          @Value("${category.paths:}") String [] paths) {
        return controller(title, description, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi clientOpenApi(@Value("${client.info.title:}") String title,
                                        @Value("${common.description:}") String description,
                                        @Value("${client.group-name:}") String groupName,
                                        @Value("${client.paths:}") String [] paths) {
        return controller(title, description, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi lnkOpenApi(@Value("${lnk.info.title:}") String title,
                                     @Value("${common.description:}") String description,
                                     @Value("${lnk.group-name:}") String groupName,
                                     @Value("${lnk.paths:}") String [] paths) {
        return controller(title, description, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi localityOpenApi(@Value("${locality.info.title:}") String title,
                                          @Value("${common.description:}") String description,
                                          @Value("${locality.group-name:}") String groupName,
                                          @Value("${locality.paths:}") String [] paths) {
        return controller(title, description, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi regionOpenApi(@Value("${region.info.title:}") String title,
                                        @Value("${common.description:}") String description,
                                        @Value("${region.group-name:}") String groupName,
                                        @Value("${region.paths:}") String [] paths) {
        return controller(title, description, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi streetOpenApi(@Value("${street.info.title:}") String title,
                                        @Value("${common.description:}") String description,
                                        @Value("${street.group-name:}") String groupName,
                                        @Value("${street.paths:}") String [] paths) {
        return controller(title, description, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi subcategoryOpenApi(@Value("${subcategory.info.title:}") String title,
                                             @Value("${common.description:}") String description,
                                             @Value("${subcategory.group-name:}") String groupName,
                                             @Value("${subcategory.paths:}") String [] paths) {
        return controller(title, description, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi complexAddressOpenApi(@Value("${complex-address.info.title:}") String title,
                                         @Value("${common.description:}") String description,
                                         @Value("${complex-address.group-name:}") String groupName,
                                         @Value("${complex-address.paths:}") String [] paths) {
        return controller(title, description, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi authOpenApi(@Value("${auth.info.title:}") String title,
                                      @Value("${common.description:}") String description,
                                      @Value("${auth.group-name:}") String groupName,
                                      @Value("${auth.paths:}") String [] paths) {
        return controller(title, description, groupName, paths)
                .build();
    }

    private GroupedOpenApi.Builder controller(String title, String description, String groupName, String [] paths) {
        return GroupedOpenApi.builder()
                .addOpenApiCustomiser(openApi -> openApi.info(new Info()
                        .title(title)
                        .description(description)))
                .group(groupName)
                .pathsToMatch(paths);
    }
}
