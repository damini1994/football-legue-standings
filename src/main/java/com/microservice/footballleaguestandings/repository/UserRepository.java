package com.microservice.footballleaguestandings.repository;

import com.microservice.footballleaguestandings.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Integer> {

  AppUser findByUsername(String username);

  boolean existsByUsername(String username);

}
