package app.writerslife.server.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender emailSender;

    public boolean sendEmailWith(String email) {
        SimpleMailMessage message = new SimpleMailMessage();

        try {
            message.setTo(email);
            message.setSubject("Test");
            message.setText("test");
            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
