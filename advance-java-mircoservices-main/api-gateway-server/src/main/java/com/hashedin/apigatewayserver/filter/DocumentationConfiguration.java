package com.hashedin.apigatewayserver.filter;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DocumentationConfiguration {

    private final RouteDefinitionLocator locator;

    @Bean
    public List<GroupedOpenApi> apis() {
        return new ArrayList<>();
    }

}