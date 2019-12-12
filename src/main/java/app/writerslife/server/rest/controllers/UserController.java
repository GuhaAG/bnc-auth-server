package app.writerslife.server.rest.controllers;

import app.writerslife.server.models.entities.LoginUser;
import app.writerslife.server.models.models.LoginUserBuilder;
import app.writerslife.server.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserManagementService service;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public LoginUser get() {
        String loggedInUsername = getLoggedInUsername();
        Optional<LoginUser> user = service.findByUsername(loggedInUsername);

        return user.get();
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


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public LoginUser add(@Valid @RequestBody LoginUserBuilder user)
            throws DuplicateUserException {
        LoginUser addLoginUser = new LoginUser(user);

        if (service.findUser(user.getUsername(), user.getEmail()).isPresent()) {
            throw new DuplicateUserException();
        }

        return service.addUser(addLoginUser);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public LoginUser update(@PathVariable Long id, @Valid @RequestBody LoginUserBuilder user)
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

        public DuplicateUserException() {
        }
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User doesn't exist")
    class UserNotExistsException extends Exception {
        private static final long serialVersionUID = 7736888161753919227L;

        public UserNotExistsException() {
        }
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED,
            reason = "This user is not allowed to access this resource")
    class UnauthorizedUserException extends Exception {
        private static final long serialVersionUID = 7736888161753919227L;

        public UnauthorizedUserException() {
        }
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


