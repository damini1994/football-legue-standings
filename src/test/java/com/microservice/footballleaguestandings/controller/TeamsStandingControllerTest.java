package com.microservice.footballleaguestandings.controller;

import com.microservice.footballleaguestandings.dto.TeamStandingDto;
import com.microservice.footballleaguestandings.exception.TeamsStandingException;
import com.microservice.footballleaguestandings.model.TeamsStandingRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@EnableWebSecurity
@WithMockUser(username = "test12121", password = "pwd", roles = "ADMIN")
public class TeamsStandingControllerTest {

    TeamsStandingRequest requestValid;
    TeamsStandingRequest requestInvalidCountry;
    TeamsStandingRequest requestInvalidLeague;
    TeamsStandingRequest requestInvalidTeam;
    TeamsStandingRequest validationFailRequest;

    @Autowired
    TeamsStandingController footballLeagueController;

    @Before
    public void before() throws Exception {
        requestValid=new TeamsStandingRequest("England","Non League Premier","Enfield Town");
        requestInvalidCountry=new TeamsStandingRequest("England1","Championship","Bournemouth");
        requestInvalidLeague=new TeamsStandingRequest("England","Championship1","Bournemouth");
        requestInvalidTeam=new TeamsStandingRequest("England","Championship","Bournemouth1");
        validationFailRequest=new TeamsStandingRequest("","Championship","Bournemouth1");
    }

    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    @Test
    public void testGetTeamStandings() throws Exception {
        ResponseEntity<TeamStandingDto> response=footballLeagueController.getStandings(requestValid);
        assertNotNull(response);
    }

    @WithMockUser(username = "user2", password = "pwd", roles = "ADMIN")
    @Test(expected = TeamsStandingException.class)
    public void testGetTeamStandingsInvalidCountry() throws Exception {
        ResponseEntity<TeamStandingDto> response=footballLeagueController.getStandings(requestInvalidCountry);
        assertNotNull(response);
    }

    @WithMockUser(username = "user3", password = "pwd", roles = "ADMIN")
    @Test(expected = TeamsStandingException.class)
    public void testGetTeamStandingsInvalidLeague() throws Exception {
        ResponseEntity<TeamStandingDto> response=footballLeagueController.getStandings(requestInvalidLeague);
        assertNotNull(response);
    }

    @WithMockUser(username = "user4", password = "pwd", roles = "ADMIN")
    @Test(expected = TeamsStandingException.class)
    public void testGetTeamStandingsInvalidTeam() throws Exception {
        ResponseEntity<TeamStandingDto> response=footballLeagueController.getStandings(requestInvalidTeam);
        assertNotNull(response);
    }

    @WithMockUser(username = "user5", password = "pwd", roles = "ADMIN")
    @Test(expected = TeamsStandingException.class)
    public void testGetTeamStandingsMethodArgumentNotValidException() throws Exception {
        ResponseEntity<TeamStandingDto> response=footballLeagueController.getStandings(requestInvalidTeam);
        assertNotNull(response);
    }

} 
