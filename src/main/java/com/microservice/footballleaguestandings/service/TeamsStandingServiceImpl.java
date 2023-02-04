package com.microservice.footballleaguestandings.service;

import com.microservice.footballleaguestandings.client.CountryFallback;
import com.microservice.footballleaguestandings.client.LeagueFallback;
import com.microservice.footballleaguestandings.client.TeamsFallback;
import com.microservice.footballleaguestandings.client.TeamsStandingFallbackFactory;
import com.microservice.footballleaguestandings.dto.TeamStandingDto;
import com.microservice.footballleaguestandings.exception.TeamsStandingException;
import com.microservice.footballleaguestandings.log.annotation.Trace;
import com.microservice.footballleaguestandings.log.eventtype.LogEventType;
import com.microservice.footballleaguestandings.model.Country;
import com.microservice.footballleaguestandings.model.Leagues;
import com.microservice.footballleaguestandings.model.TeamsStanding;
import com.microservice.footballleaguestandings.model.TeamsStandingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TeamsStandingServiceImpl implements  TeamsStandingService{

  @Autowired
  TeamsStandingFallbackFactory teamsStandingFallbackFactory;

  @Trace(type = LogEventType.SERVICE)
  public TeamStandingDto getTeamStanding(TeamsStandingRequest teamsStandingRequest) {

    TeamsStanding teamsStandingDefault = getDefaultTeamStanding(teamsStandingRequest);
    List<Country> countries = teamsStandingFallbackFactory.getFallback("country", 0);
    Country country = getCountryByName(teamsStandingRequest, countries);
    if (!isValidateCountryResponse(teamsStandingRequest, teamsStandingDefault, country)) {
      return TeamStandingDto.from(teamsStandingDefault);
    }
    teamsStandingDefault.setCountryId(country.getId());

    List<Leagues> leaguesList = teamsStandingFallbackFactory.getFallback("league", country.getId());
    Leagues leagues = getLeaguesByName(teamsStandingRequest, leaguesList);
    if (!isValidLeagueResponse(teamsStandingRequest, teamsStandingDefault, leagues)) {
      return(TeamStandingDto.from(teamsStandingDefault));
    }
    teamsStandingDefault.setLeagueId(leagues.getLeagueId());

    List<TeamsStanding> teamsStandings = teamsStandingFallbackFactory.getFallback("team", leagues.getLeagueId());
    TeamsStanding teamsStanding = getTeamName(teamsStandingRequest, teamsStandings);
    if (!isValidTeamResponse(teamsStandingRequest, teamsStandingDefault, teamsStanding)) {
      return(TeamStandingDto.from(teamsStandingDefault));
    }
    teamsStanding.setCountryId(country.getId());

    return TeamStandingDto.from(teamsStanding);
  }

  private Country getCountryByName(TeamsStandingRequest teamsStandingRequest,
                                   List<Country> countries) {
    return countries.stream()
        .filter(c -> teamsStandingRequest.getCountryName().equalsIgnoreCase(c.getName())).findFirst()
        .orElse(null);
  }

  private Leagues getLeaguesByName(TeamsStandingRequest teamsStandingRequest, List<Leagues> leaguesList) {
    return leaguesList.stream()
        .filter(l -> teamsStandingRequest.getLeagueName().equalsIgnoreCase(l.getLeagueName()))
        .findFirst().orElse(null);
  }

  private TeamsStanding getTeamName(TeamsStandingRequest teamsStandingRequest, List<TeamsStanding> teamsStandings) {
    return teamsStandings.stream()
        .filter(t -> teamsStandingRequest.getTeamName().equalsIgnoreCase(t.getTeamName()))
        .findFirst().orElse(null);
  }

  private boolean isValidLeagueResponse(TeamsStandingRequest teamsStandingRequest, TeamsStanding teamsStandingDefault, Leagues leagues) {
    if (Objects.isNull(leagues)) {
      throw new TeamsStandingException("leagues Not Found by name " + teamsStandingRequest.getLeagueName(), HttpStatus.NOT_FOUND);
    }
    log.info("league found {}", leagues.toString());
    if (leagues.getLeagueId() == 0) {
      return false;
    }
    return true;
  }

  private boolean isValidateCountryResponse(TeamsStandingRequest teamsStandingRequest, TeamsStanding teamsStandingDefault, Country country) {

    if (Objects.isNull(country)) {
      throw new TeamsStandingException("Country Not Found by name " + teamsStandingRequest.getCountryName(), HttpStatus.NOT_FOUND);
    }
    log.info("Country found {}", country.toString());

    if (country.getId() == 0) {
      teamsStandingDefault.setCountryId(0);
      return false;
    }
    return true;
  }

  private boolean isValidTeamResponse(TeamsStandingRequest teamsStandingRequest, TeamsStanding teamsStandingDefault, TeamsStanding team) {
    if (Objects.isNull(team)) {
      throw new TeamsStandingException("Team not Found by name " + teamsStandingRequest.getTeamName(), HttpStatus.NOT_FOUND);
    }
    log.info("Team found {}", team.toString());

    if (team.getTeamId() == 0) {
      return false;
    }
    return true;
  }

  private TeamsStanding getDefaultTeamStanding(TeamsStandingRequest teamsStandingRequest) {
    TeamsStanding teamsStanding = new TeamsStanding();
    teamsStanding.setTeamName(teamsStandingRequest.getTeamName());
    teamsStanding.setCountryName(teamsStandingRequest.getCountryName());
    teamsStanding.setLeagueName(teamsStandingRequest.getLeagueName());
    return teamsStanding;
  }

}
