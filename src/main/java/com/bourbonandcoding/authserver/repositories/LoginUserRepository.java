package com.bourbonandcoding.authserver.repositories;

import java.util.Optional;

import com.bourbonandcoding.authserver.models.entities.LoginUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface LoginUserRepository extends CrudRepository<LoginUser, Long> {

  @Query("SELECT u FROM LoginUser u WHERE u.username = ?1 or u.email = ?2")
  public Optional<LoginUser> findUserByEmailOrUsername(String username, String email);

  @Query("SELECT u FROM LoginUser u WHERE u.username = ?1")
  public Optional<LoginUser> findUserByUsername(String username);

  @Query("SELECT u FROM LoginUser u WHERE u.email = ?1")
  public Optional<LoginUser> findUserByEmail(String email);
}
