package com.microservice.footballleaguestandings.service;

import com.microservice.footballleaguestandings.dto.TeamStandingDto;
import com.microservice.footballleaguestandings.model.TeamsStandingRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public interface TeamsStandingService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    TeamStandingDto getTeamStanding(TeamsStandingRequest teamsStandingRequest);
}
