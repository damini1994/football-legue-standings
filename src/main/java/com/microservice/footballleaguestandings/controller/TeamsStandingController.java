package com.microservice.footballleaguestandings.controller;

import com.microservice.footballleaguestandings.dto.TeamStandingDto;
import com.microservice.footballleaguestandings.log.annotation.Trace;
import com.microservice.footballleaguestandings.log.eventtype.LogEventType;
import com.microservice.footballleaguestandings.model.TeamsStandingRequest;
import com.microservice.footballleaguestandings.service.TeamsStandingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "Football-League-API")
@RequestMapping("teams/standing")
public class TeamsStandingController {

  private final TeamsStandingService teamStandingService;

  @Autowired
  public TeamsStandingController(TeamsStandingService teamStandingService) {
    this.teamStandingService = teamStandingService;
  }

  @GetMapping
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiOperation(value = "Get Standings of team playing Football league", response = TeamStandingDto.class, authorizations = { @Authorization(value="apiKey") })
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 403, message = "Access denied"),
          @ApiResponse(code = 404, message = "Not Found"),
          @ApiResponse(code = 500, message = "Something went wrong")})
  @Trace(type = LogEventType.CONTROLLER)
  public ResponseEntity<TeamStandingDto> getStandings(@Valid TeamsStandingRequest teamsStandingRequest) {
    log.info("Request {}", teamsStandingRequest.toString());
    return ResponseEntity.ok(teamStandingService.getTeamStanding(teamsStandingRequest));
  }
}
