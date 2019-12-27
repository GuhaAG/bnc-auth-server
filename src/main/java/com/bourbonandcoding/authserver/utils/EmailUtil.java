package com.bourbonandcoding.authserver.utils;

import com.bourbonandcoding.authserver.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private String generatePasswordResetToken(UserDetails userDetails) {
        return jwtTokenUtil.generateToken(userDetails);
    }

    public boolean sendPasswordResetEmail(String appUrl, String email, String username) {
        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);
        return sendEmailWithToken(appUrl, email, generatePasswordResetToken(userDetails));
    }

    public boolean sendEmailWithToken(String appUrl, String email, String token) {
        String emailMessage = "Navigate to this URL to reset your password ";
        SimpleMailMessage message = new SimpleMailMessage();

        try {
            message.setTo(email);
            message.setSubject("[Writerslife] Password Reset");
            message.setText(emailMessage + appUrl + "/UpdatePassword?token=" + token);
            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
