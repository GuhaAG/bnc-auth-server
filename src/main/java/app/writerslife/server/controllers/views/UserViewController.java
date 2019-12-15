package app.writerslife.server.controllers.views;

import app.writerslife.server.models.dto.PasswordChangeRequest;
import app.writerslife.server.models.entities.LoginUser;
import app.writerslife.server.models.models.LoginUserBuilder;
import app.writerslife.server.services.UserManagementService;
import app.writerslife.server.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserViewController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserManagementService service;

    @RequestMapping("/UpdatePassword")
    public String showChangePasswordPage(@RequestParam("token") String token, final Model model) {
        model.addAttribute("username", jwtTokenUtil.getUsernameFromToken(token));
        model.addAttribute("token", token);
        if (!jwtTokenUtil.validateToken(token)) {
            return "errorPage";
        }
        return "UpdatePasswordPage";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/users/savePassword", method = RequestMethod.POST)
    public boolean savePassword(@Valid PasswordChangeRequest passwordChangeRequest) {
        String username = passwordChangeRequest.getUsername();
        String password = passwordChangeRequest.getPassword();

        if (jwtTokenUtil.validateToken(passwordChangeRequest.getToken())) {
            Optional<LoginUser> loginUser = service.findByUsername(username);
            if (loginUser.isPresent()) {
                LoginUserBuilder loginUserBuilder = new LoginUserBuilder();
                loginUserBuilder.setPassword(password);
                LoginUser updatedUser = new LoginUser(loginUserBuilder, loginUser.get());
                updatedUser.setId(loginUser.get().getId());

                service.addUser(updatedUser);

                return true;
            }
        }
        return false;
    }
}





