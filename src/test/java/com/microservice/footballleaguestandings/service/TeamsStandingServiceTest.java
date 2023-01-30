package com.microservice.footballleaguestandings.service;

import com.microservice.footballleaguestandings.dto.TeamStandingDto;
import com.microservice.footballleaguestandings.exception.TeamsStandingException;
import com.microservice.footballleaguestandings.model.TeamsStandingRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@WithMockUser(username = "test2", password = "pwd", roles = "ADMIN")
public class TeamsStandingServiceTest {
    TeamsStandingRequest request;

    @Autowired
    TeamsStandingService teamsStandingService;

    @Before
    public void before() throws Exception {
        request=new TeamsStandingRequest("England","Championship","Bournemouth");
    }

    @WithMockUser(username = "test11", password = "pwd", roles = "ADMIN")
    @Test(expected = TeamsStandingException.class)
    public void testGetTeamStandings() throws Exception {
      TeamStandingDto response=teamsStandingService.getTeamStanding(request);
      assertNotNull(response);
    }
} 
