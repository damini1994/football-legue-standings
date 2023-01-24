package com.microservice.footballleaguestandings.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class TeamsStandingRequest {

  @NotBlank
  private String countryName;

  @NotBlank
  private String leagueName;

  @NotBlank
  private String teamName;

}
