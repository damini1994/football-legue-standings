package com.microservice.footballleaguestandings.client;

import com.microservice.footballleaguestandings.config.model.LeagueProperties;
import com.microservice.footballleaguestandings.log.annotation.Trace;
import com.microservice.footballleaguestandings.log.eventtype.LogEventType;
import com.microservice.footballleaguestandings.model.Country;
import com.microservice.footballleaguestandings.model.Leagues;
import com.microservice.footballleaguestandings.model.TeamsStanding;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class TeamsStandingFallback {

  private static final String APIKEY = "APIkey";
  private static final String ACTION = "action";
  private static final String COUNTRY_ID = "country_id";
  private static final String LEAGUE_ID = "league_id";

  private final RestTemplate restTemplate;

  @Autowired
  private LeagueProperties leagueProperties;

  @Autowired
  public TeamsStandingFallback(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @HystrixCommand(fallbackMethod = "getCountries_Fallback",  commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
  @Trace(type = LogEventType.REST_CLIENT)
  public Country[] getCountries() {
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put(ACTION, leagueProperties.getActionCountries());

    UriComponentsBuilder builder = getUriComponentsBuilder(leagueProperties.getBaseUrl(), queryParams);
    Country[] countries= restTemplate.getForEntity(builder.toUriString(), Country[].class).getBody();
    return countries;
  }

  @HystrixCommand(fallbackMethod = "getLeagues_Fallback",  commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
  @Trace(type = LogEventType.REST_CLIENT)
  public Leagues[] getLeagues(int countryId) {
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put(ACTION, leagueProperties.getActionLeagues());
    queryParams.put(COUNTRY_ID, String.valueOf(countryId));

    UriComponentsBuilder builder = getUriComponentsBuilder(leagueProperties.getBaseUrl(), queryParams);
    Leagues[] leagues=restTemplate.getForEntity(builder.toUriString(), Leagues[].class).getBody();
    return leagues;
  }

  @HystrixCommand(fallbackMethod = "getTeamStanding_Fallback",  commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
  @Trace(type = LogEventType.REST_CLIENT)
  public TeamsStanding[] getTeamStanding(int leagueId) {
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put(ACTION, leagueProperties.getActionStandings());
    queryParams.put(LEAGUE_ID, String.valueOf(leagueId));

    UriComponentsBuilder builder = getUriComponentsBuilder(leagueProperties.getBaseUrl(), queryParams);
    TeamsStanding[] teamsStanding=restTemplate.getForEntity(builder.toUriString(), TeamsStanding[].class).getBody();

    return teamsStanding;
  }

  private Country[] getCountries_Fallback() {
    return new Country[]{new Country()};
  }

  private Leagues[] getLeagues_Fallback(int countryId) {
    Leagues leagues = new Leagues();
    leagues.setCountryId(countryId);
    return new Leagues[]{leagues};
  }

  private TeamsStanding[] getTeamStanding_Fallback(int leagueId) {
    TeamsStanding teamsStanding = new TeamsStanding();
    teamsStanding.setLeagueId(leagueId);
    return new TeamsStanding[]{teamsStanding};
  }

  private UriComponentsBuilder getUriComponentsBuilder(String url, Map<String, String> queryParams) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam(APIKEY, leagueProperties.getApiKey());
    queryParams.forEach(builder::queryParam);
    return builder;
  }

  private HttpHeaders getHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    return headers;
  }
}
