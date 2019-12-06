package app.writerslife.server.rest.controllers;

import java.util.Optional;
import javax.validation.Valid;

import app.writerslife.server.models.entities.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import app.writerslife.server.models.models.UserBuilder;
import app.writerslife.server.services.UserManagementService;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserManagementService service;

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public LoginUser add(@Valid @RequestBody UserBuilder user)
      throws DuplicateUserException {
    LoginUser addLoginUser = new LoginUser(user);

    if (service.findUser(user.getUsername(), user.getEmail()).isPresent()) {
      throw new DuplicateUserException();
    }

    return service.addUser(addLoginUser);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public LoginUser getByUserId(@PathVariable Long id)
      throws UserNotExistsException, UnauthorizedUserException {
    Optional<LoginUser> user = service.findUser(id);

    if (!user.isPresent()) {
      throw new UserNotExistsException();
    }

    String loggedInUsername = getLoggedInUsername();

    LoginUser idLoginUser = user.get();

    if (!loggedInUsername.equals(idLoginUser.getUsername())) {
      throw new UnauthorizedUserException();
    }

    return user.get();

  }

  @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public LoginUser update(@PathVariable Long id, @Valid @RequestBody UserBuilder user)
      throws UserNotExistsException, UnauthorizedUserException {
    Optional<LoginUser> thisUser = service.findUser(id);

    if (!thisUser.isPresent()) {
      throw new UserNotExistsException();
    }

    LoginUser updatedUser = new LoginUser(user);
    updatedUser.setId(id);

    String loggedInUsername = getLoggedInUsername();

    if (loggedInUsername.equals(updatedUser.getUsername())) {
      throw new UnauthorizedUserException();
    }

    return service.addUser(updatedUser);
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.deleteUser(id);
  }

  @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Username or Email already exists")
  class DuplicateUserException extends Exception {
    private static final long serialVersionUID = 7736888161753919227L;

    public DuplicateUserException() {}
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User doesn't exist")
  class UserNotExistsException extends Exception {
    private static final long serialVersionUID = 7736888161753919227L;

    public UserNotExistsException() {}
  }

  @ResponseStatus(code = HttpStatus.UNAUTHORIZED,
      reason = "This user is not allowed to access this resource")
  class UnauthorizedUserException extends Exception {
    private static final long serialVersionUID = 7736888161753919227L;

    public UnauthorizedUserException() {}
  }

  private String getLoggedInUsername() {
    String loggedInUsername;

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetails) {
      loggedInUsername = ((UserDetails) principal).getUsername();
    } else {
      loggedInUsername = principal.toString();
    }
    return loggedInUsername;
  }
}


