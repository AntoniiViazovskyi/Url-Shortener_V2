package com.goit.config.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        name = "BearerAuth")

public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi apiLinksV1() {
        return GroupedOpenApi.builder()
                .group("URLs API v1")
                .pathsToMatch("/api/v1/notes/**")
                .build();
    }

//    @Bean
//    public GroupedOpenApi apiUsersV1() {
//        return GroupedOpenApi.builder()
//                .group("Users API v1")
//                .pathsToMatch("/api/v1/users/**")
//                .build();
//    }

    @Bean
    public GroupedOpenApi apiAuth() {
        return GroupedOpenApi.builder()
                .group("Users-Auth API")
                .pathsToMatch("/auth/**", "/users/**")
//                .pathsToMatch("/auth/**")
//                .pathsToMatch("/users/**")
                .build();
    }

//    @Bean
//    public GroupedOpenApi apiV2() {
//        return GroupedOpenApi.builder()
//                .group("v2")
//                .pathsToMatch("/auth/**")
//                .build();
//    }

//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("springshop-public")
//                .pathsToMatch("/public/**")
//                .build();
//    }
//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("springshop-admin")
//                .pathsToMatch("/admin/**")
//                .build();
//    }

    @Bean
    @Primary
    public OpenAPI customOpenAPIv1() {
        return new OpenAPI()
                .info(new Info()
                        .title("Shortener API V1")
                        .version("v1")
                        .description("API Documentation"));
    }

//    @Bean
//    public OpenAPI customOpenAPIv2() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("My API V2")
//                        .version("v2")
//                        .description("Documentation for API version 2"));
//    }

//    @Bean
//    public OpenAPI myOpenAPI() {
//
//        Server devServer = new Server();
//        devServer.setUrl("http://localhost:8080");
//        devServer.setDescription("Server URL in Development environment");
//
//        return new OpenAPI().servers(List.of(devServer));
////                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
////                .components(
////                        new Components().addSecuritySchemes("bearerAuth",
////                        new SecurityScheme()
////                                .type(SecurityScheme.Type.HTTP)
////                                .scheme("bearer")
////                                .bearerFormat("JWT")))
////                .info(new Info().title("API").version("1.0").description("API Documentation"));
//    }



}
