package com.microservice.footballleaguestandings.controller;

import com.microservice.footballleaguestandings.dto.UserDataDTO;
import com.microservice.footballleaguestandings.model.AppUser;
import com.microservice.footballleaguestandings.service.UserServiceImpl;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@Api(tags = "Authentication")
public class UserController {

  @Autowired
  UserServiceImpl userService;

  @Autowired
  ModelMapper modelMapper;

  @PostMapping("/authenticate")
  @ApiOperation(value = "Authenticates user and returns its JWT token.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Something went wrong"),
      @ApiResponse(code = 422, message = "Invalid username/password supplied")})
  public String authenticate(
      @ApiParam("Username") @RequestParam String username,
      @ApiParam("Password") @RequestParam String password) {
    log.info("Username {}", username);
    return userService.generateToken(username, password);
  }

  @PostMapping("/signup")
  @ApiOperation(value = "${UserController.signup}")
  @ApiResponses(value = {
          @ApiResponse(code = 400, message = "Something went wrong"),
          @ApiResponse(code = 403, message = "Access denied"),
          @ApiResponse(code = 422, message = "Username is already in use")})
  public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
    return userService.signup(modelMapper.map(user, AppUser.class));
  }

  @GetMapping("/refresh")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public String refresh(HttpServletRequest req) {
    return userService.refresh(req.getRemoteUser());
  }

}
