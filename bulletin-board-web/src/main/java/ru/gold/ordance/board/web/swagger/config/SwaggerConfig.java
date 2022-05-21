package ru.gold.ordance.board.web.swagger.config;

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
    public GroupedOpenApi photoOpenApi(@Value("${photo.info.title:}") String title,
                                       @Value("${photo.group-name:}") String groupName,
                                       @Value("${photo.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi addressOpenApi(@Value("${address.info.title:}") String title,
                                         @Value("${address.group-name:}") String groupName,
                                         @Value("${address.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi advertisementOpenApi(@Value("${advertisement.info.title:}") String title,
                                               @Value("${advertisement.group-name:}") String groupName,
                                               @Value("${advertisement.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi categoryOpenApi(@Value("${category.info.title:}") String title,
                                          @Value("${category.group-name:}") String groupName,
                                          @Value("${category.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi clientOpenApi(@Value("${client.info.title:}") String title,
                                        @Value("${client.group-name:}") String groupName,
                                        @Value("${client.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi lnkOpenApi(@Value("${lnk.info.title:}") String title,
                                     @Value("${lnk.group-name:}") String groupName,
                                     @Value("${lnk.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi localityOpenApi(@Value("${locality.info.title:}") String title,
                                          @Value("${locality.group-name:}") String groupName,
                                          @Value("${locality.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi regionOpenApi(@Value("${region.info.title:}") String title,
                                        @Value("${region.group-name:}") String groupName,
                                        @Value("${region.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi streetOpenApi(@Value("${street.info.title:}") String title,
                                        @Value("${street.group-name:}") String groupName,
                                        @Value("${street.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi subcategoryOpenApi(@Value("${subcategory.info.title:}") String title,
                                             @Value("${subcategory.group-name:}") String groupName,
                                             @Value("${subcategory.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi complexAddressOpenApi(@Value("${complex-address.info.title:}") String title,
                                         @Value("${complex-address.group-name:}") String groupName,
                                         @Value("${complex-address.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    @Bean
    public GroupedOpenApi authOpenApi(@Value("${auth.info.title:}") String title,
                                      @Value("${auth.group-name:}") String groupName,
                                      @Value("${auth.paths:}") String [] paths) {
        return controller(title, groupName, paths)
                .build();
    }

    private GroupedOpenApi.Builder controller(String title, String groupName, String [] paths) {
        return GroupedOpenApi.builder()
                .addOpenApiCustomiser(openApi -> openApi.info(new Info()
                        .title(title)))
                .group(groupName)
                .pathsToMatch(paths);
    }
}
