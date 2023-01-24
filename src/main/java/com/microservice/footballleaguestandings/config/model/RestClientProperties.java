package com.microservice.footballleaguestandings.config.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rest.client")
@Getter
@Setter
public class RestClientProperties {

    private int readTimeout;
    private int connectTimeout;
}
