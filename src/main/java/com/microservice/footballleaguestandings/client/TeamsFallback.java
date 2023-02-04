package com.microservice.footballleaguestandings.client;

import com.microservice.footballleaguestandings.config.model.LeagueProperties;
import com.microservice.footballleaguestandings.log.annotation.Trace;
import com.microservice.footballleaguestandings.log.eventtype.LogEventType;
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
public class TeamsFallback {

  private static final String ACTION = "action";
  private static final String LEAGUE_ID = "league_id";

  private final RestTemplate restTemplate;

  @Autowired
  private LeagueProperties leagueProperties;

  @Autowired
  private FootballLeagueUtility footballLeagueUtility;
  @Autowired
  public TeamsFallback(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @HystrixCommand(fallbackMethod = "getTeamStanding_Fallback",  commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
  @Trace(type = LogEventType.REST_CLIENT)
  public TeamsStanding[] getTeamStanding(int leagueId) {
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put(ACTION, leagueProperties.getActionStandings());
    queryParams.put(LEAGUE_ID, String.valueOf(leagueId));

    UriComponentsBuilder builder = footballLeagueUtility.getUriComponentsBuilder(leagueProperties.getBaseUrl(), queryParams);
    TeamsStanding[] teamsStanding=restTemplate.getForEntity(builder.toUriString(), TeamsStanding[].class).getBody();

    return teamsStanding;
  }

  private TeamsStanding[] getTeamStanding_Fallback(int leagueId) {
    TeamsStanding teamsStanding = new TeamsStanding();
    teamsStanding.setLeagueId(leagueId);
    return new TeamsStanding[]{teamsStanding};
  }


}
