package com.auric.entertainment.auric_backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "whatsapp")
public class WhatsappProperties {

    private String baseUrl;
    private String phoneNumberId;
    private String accessToken;
    private String targetNumber;
}
