package com.microservice.footballleaguestandings.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TeamsStandingFallbackFactory {

    @Autowired
    TeamsFallback teamStandingFallback;

    @Autowired
    LeagueFallback leagueFallback;

    @Autowired
    CountryFallback countryFallback;

    public List getFallback(String type, int id){

        if (type == "country"){
            return new ArrayList<>(Arrays.asList(countryFallback.getCountries()));
        } else if (type == "league"){
            return new ArrayList<>(Arrays.asList(leagueFallback.getLeagues(id)));
        } else  if (type == "team"){
            return new ArrayList<>(Arrays.asList(teamStandingFallback.getTeamStanding(id)));
        } else{
            return new ArrayList();
        }
    }
}
