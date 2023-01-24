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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
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

@Test
public void testGetTeamStandings() throws Exception {
    ResponseEntity<TeamStandingDto> response=footballLeagueController.getStandings(requestValid);
    assertNotNull(response);
}

    @Test(expected = TeamsStandingException.class)
    public void testGetTeamStandingsInvalidCountry() throws Exception {
        ResponseEntity<TeamStandingDto> response=footballLeagueController.getStandings(requestInvalidCountry);
        assertNotNull(response);
    }

    @Test(expected = TeamsStandingException.class)
    public void testGetTeamStandingsInvalidLeague() throws Exception {
        ResponseEntity<TeamStandingDto> response=footballLeagueController.getStandings(requestInvalidLeague);
        assertNotNull(response);
    }

    @Test(expected = TeamsStandingException.class)
    public void testGetTeamStandingsInvalidTeam() throws Exception {
        ResponseEntity<TeamStandingDto> response=footballLeagueController.getStandings(requestInvalidTeam);
        assertNotNull(response);
    }

    @Test(expected = TeamsStandingException.class)
    public void testGetTeamStandingsMethodArgumentNotValidException() throws Exception {
        ResponseEntity<TeamStandingDto> response=footballLeagueController.getStandings(requestInvalidTeam);
        assertNotNull(response);
    }

} 
