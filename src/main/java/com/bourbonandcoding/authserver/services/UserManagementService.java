package com.bourbonandcoding.authserver.services;

import java.util.Optional;

import com.bourbonandcoding.authserver.models.entities.LoginUser;
import com.bourbonandcoding.authserver.repositories.LoginUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManagementService {

  @Autowired
  private LoginUserRepository repository;

  public LoginUser addUser(LoginUser loginUser) {
    return repository.save(loginUser);
  }

  public Optional<LoginUser> findUser(long id) {
    return repository.findById(id);
  }

  public Optional<LoginUser> findUser(String username, String email) {
    return repository.findUserByEmailOrUsername(username, email);
  }

  public Optional<LoginUser> findByUsername(String username) {
    return repository.findUserByUsername(username);
  }

  public Optional<LoginUser> findByEmail(String email) {
    return repository.findUserByEmail(email);
  }

  public void deleteUser(long userId) {
    repository.deleteById(userId);
  }
}
