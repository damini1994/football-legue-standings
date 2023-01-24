package com.microservice.footballleaguestandings.controller;

import com.microservice.footballleaguestandings.dto.TeamStandingDto;
import com.microservice.footballleaguestandings.log.annotation.Trace;
import com.microservice.footballleaguestandings.log.eventtype.LogEventType;
import com.microservice.footballleaguestandings.model.TeamsStandingRequest;
import com.microservice.footballleaguestandings.service.TeamsStandingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("teams/standing")
public class TeamsStandingController {

  private final TeamsStandingService teamStandingService;

  @Autowired
  public TeamsStandingController(TeamsStandingService teamStandingService) {
    this.teamStandingService = teamStandingService;
  }

  @Operation(summary = "Fetch standings of a team playing league football match")
  @GetMapping
  @Trace(type = LogEventType.CONTROLLER)
  public ResponseEntity<TeamStandingDto> getStandings(@Valid TeamsStandingRequest teamsStandingRequest) {
    log.info("Request {}", teamsStandingRequest.toString());
    return ResponseEntity.ok(teamStandingService.getTeamStanding(teamsStandingRequest));
  }
}
