package app.writerslife.server.controllers.views;

import app.writerslife.server.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserViewController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/UpdatePassword")
    public String showChangePasswordPage(@RequestParam("token") String token) {
        if (!jwtTokenUtil.validateToken(token)) {
            return "errorPage";
        }
        return "UpdatePasswordPage";
    }
}





