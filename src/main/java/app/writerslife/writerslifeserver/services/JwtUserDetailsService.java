package app.writerslife.writerslifeserver.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import app.writerslife.writerslifeserver.models.entities.WritersUser;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private WritersUserManagementService service;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<WritersUser> optUser = service.findByUsername(username);

    if (!optUser.isPresent()) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    WritersUser user = optUser.get();

    return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }

}
