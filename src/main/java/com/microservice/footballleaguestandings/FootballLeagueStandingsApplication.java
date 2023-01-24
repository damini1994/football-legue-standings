package com.microservice.footballleaguestandings;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Football League Standings", version = "1.0", description = "Microservice to find standings of team playing footabll league."))
public class FootballLeagueStandingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballLeagueStandingsApplication.class, args);
	}

}
