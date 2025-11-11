package com.auric.entertainment.auric_backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Auric Entertainment API",
                version = "v1",
                description = "Backend endpoints for Auth, Events, and Bookings"
        )
)
public class OpenApiConfig {

}
