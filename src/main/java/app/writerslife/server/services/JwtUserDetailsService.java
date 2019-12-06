package app.writerslife.server.services;

import java.util.ArrayList;
import java.util.Optional;

import app.writerslife.server.models.entities.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserManagementService service;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<LoginUser> optUser = service.findByUsername(username);

    if (!optUser.isPresent()) {
      optUser = service.findByEmail(username);
      if (!optUser.isPresent()) {
        throw new UsernameNotFoundException("User not found");
      }
    }

    LoginUser loginUser = optUser.get();

    return new org.springframework.security.core.userdetails.User(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>());
  }

}
