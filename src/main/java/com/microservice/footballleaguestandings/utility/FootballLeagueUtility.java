package com.microservice.footballleaguestandings.utility;

import com.microservice.footballleaguestandings.config.model.LeagueProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class FootballLeagueUtility {

    private static final String APIKEY = "APIkey";

    @Autowired
    private LeagueProperties leagueProperties;

    public UriComponentsBuilder getUriComponentsBuilder(String url, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam(APIKEY, leagueProperties.getApiKey());
        queryParams.forEach(builder::queryParam);
        return builder;
    }
}
