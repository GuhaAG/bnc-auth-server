package com.bourbonandcoding.authserver.controllers.views;

import com.bourbonandcoding.authserver.models.dto.PasswordChangeRequest;
import com.bourbonandcoding.authserver.models.entities.LoginUser;
import com.bourbonandcoding.authserver.models.models.LoginUserBuilder;
import com.bourbonandcoding.authserver.services.UserManagementService;
import com.bourbonandcoding.authserver.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    @Autowired
    Environment environment;

    @RequestMapping("/UpdatePassword")
    public String showChangePasswordPage(@RequestParam("token") String token, final Model model) {
        String host = environment.getProperty("server.host");
        String port = environment.getProperty("server.port");

        model.addAttribute("username", jwtTokenUtil.getUsernameFromToken(token));
        model.addAttribute("token", token);
        model.addAttribute("server_address", host + ":" + port);

        if (!jwtTokenUtil.validateToken(token)) {
            return "errorPage";
        }
        return "UpdatePasswordPage";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/users/savePassword", method = RequestMethod.POST)
    public String savePassword(@Valid PasswordChangeRequest passwordChangeRequest) {
        String username = passwordChangeRequest.getUsername();
        String password = passwordChangeRequest.getPassword();

        if (jwtTokenUtil.validateToken(passwordChangeRequest.getToken())) {
            try {
                Optional<LoginUser> loginUser = service.findByUsername(username);
                if (loginUser.isPresent()) {
                    LoginUserBuilder loginUserBuilder = new LoginUserBuilder();
                    loginUserBuilder.setPassword(password);
                    LoginUser updatedUser = new LoginUser(loginUserBuilder, loginUser.get());
                    updatedUser.setId(loginUser.get().getId());

                    service.addUser(updatedUser);

                    return "success";
                }
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return "expired";
    }
}





