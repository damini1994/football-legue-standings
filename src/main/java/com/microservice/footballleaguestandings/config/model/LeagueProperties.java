package com.microservice.footballleaguestandings.config.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "football.league")
@Getter
@Setter
public class LeagueProperties {

    private String apiKey;
    private String baseUrl;
    private String actionStandings;
    private String actionCountries;
    private String actionLeagues;

}
