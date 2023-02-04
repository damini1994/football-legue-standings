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
public class LeagueFallback {
    private static final String ACTION = "action";
    private static final String COUNTRY_ID = "country_id";

    private final RestTemplate restTemplate;

    @Autowired
    private LeagueProperties leagueProperties;

    @Autowired
    private FootballLeagueUtility footballLeagueUtility;

    @Autowired
    public LeagueFallback(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getLeagues_Fallback",  commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
    @Trace(type = LogEventType.REST_CLIENT)
    public Leagues[] getLeagues(int countryId) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(ACTION, leagueProperties.getActionLeagues());
        queryParams.put(COUNTRY_ID, String.valueOf(countryId));

        UriComponentsBuilder builder = footballLeagueUtility.getUriComponentsBuilder(leagueProperties.getBaseUrl(), queryParams);
        Leagues[] leagues=restTemplate.getForEntity(builder.toUriString(), Leagues[].class).getBody();
        return leagues;
    }

    private Leagues[] getLeagues_Fallback(int countryId) {
        Leagues leagues = new Leagues();
        leagues.setCountryId(countryId);
        return new Leagues[]{leagues};
    }

}
