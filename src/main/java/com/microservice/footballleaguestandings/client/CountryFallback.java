package com.microservice.footballleaguestandings.client;

import com.microservice.footballleaguestandings.config.model.LeagueProperties;
import com.microservice.footballleaguestandings.log.annotation.Trace;
import com.microservice.footballleaguestandings.log.eventtype.LogEventType;
import com.microservice.footballleaguestandings.model.Country;
import com.microservice.footballleaguestandings.model.Leagues;
import com.microservice.footballleaguestandings.model.TeamsStanding;
import com.microservice.footballleaguestandings.utility.FootballLeagueUtility;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class CountryFallback {

    private static final String ACTION = "action";

    private final RestTemplate restTemplate;

    @Autowired
    private LeagueProperties leagueProperties;

    @Autowired
    private FootballLeagueUtility footballLeagueUtility;

    @Autowired
    public CountryFallback(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getCountries_Fallback",  commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
    @Trace(type = LogEventType.REST_CLIENT)
    public Country[] getCountries() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(ACTION, leagueProperties.getActionCountries());

        UriComponentsBuilder builder = footballLeagueUtility.getUriComponentsBuilder(leagueProperties.getBaseUrl(), queryParams);
        Country[] countries= restTemplate.getForEntity(builder.toUriString(), Country[].class).getBody();
        return countries;
    }

    private Country[] getCountries_Fallback() {
        return new Country[]{new Country()};
    }

}
