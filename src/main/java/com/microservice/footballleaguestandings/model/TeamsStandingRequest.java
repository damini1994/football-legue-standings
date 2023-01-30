package com.microservice.footballleaguestandings.model;

import lombok.*;

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
