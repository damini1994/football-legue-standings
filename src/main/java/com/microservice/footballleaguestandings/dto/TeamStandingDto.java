package com.microservice.footballleaguestandings.dto;

import com.microservice.footballleaguestandings.model.TeamsStanding;
import lombok.Data;

import java.util.Objects;

@Data
public class TeamStandingDto {

  private String country;
  private String league;
  private String team;
  private int overallPosition;

  public static TeamStandingDto from(TeamsStanding teamsStanding) {
    TeamStandingDto teamStandingDto = new TeamStandingDto();
    if (Objects.nonNull(teamsStanding)) {
      teamStandingDto.setCountry("(" + teamsStanding.getCountryId() + ") - " + teamsStanding.getCountryName());
      teamStandingDto.setLeague("(" + teamsStanding.getLeagueId() + ") - " + teamsStanding.getLeagueName());
      teamStandingDto.setTeam("(" + teamsStanding.getTeamId() + ") - " + teamsStanding.getTeamName());
      teamStandingDto.setOverallPosition(teamsStanding.getOverallPosition());
    }
    return teamStandingDto;
  }
}
