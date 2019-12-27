package com.bourbonandcoding.authserver.models.entities;

import com.bourbonandcoding.authserver.models.models.LoginUserBuilder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "LoginUsers", schema = "public")
public class LoginUser {

    public LoginUser() {
    }

    public LoginUser(LoginUserBuilder loginUserBuilder) {
        this.username = loginUserBuilder.getUsername();
        this.email = loginUserBuilder.getEmail();
        this.firstname = loginUserBuilder.getFirstname();
        this.lastname = loginUserBuilder.getLastname();
        this.nomdeplume = loginUserBuilder.getNomdeplume();
        this.password = new BCryptPasswordEncoder().encode(loginUserBuilder.getPassword());
        this.createDate = LocalDateTime.now();
    }

    public LoginUser(LoginUserBuilder updatedLoginUserBuilder, LoginUser oldUser) {
        this.username = oldUser.getUsername();
        if (StringUtils.isNotEmpty(updatedLoginUserBuilder.getEmail())) {
            this.email = updatedLoginUserBuilder.getEmail();
        } else {
            this.email = oldUser.getEmail();
        }
        if (updatedLoginUserBuilder.getFirstname() != null) {
            this.firstname = updatedLoginUserBuilder.getFirstname();
        } else {
            this.firstname = oldUser.getFirstname();
        }
        if (updatedLoginUserBuilder.getLastname() != null) {
            this.lastname = updatedLoginUserBuilder.getLastname();
        } else {
            this.lastname = oldUser.getLastname();
        }
        if (updatedLoginUserBuilder.getNomdeplume() != null) {
            this.nomdeplume = updatedLoginUserBuilder.getNomdeplume();
        } else {
            this.nomdeplume = oldUser.getNomdeplume();
        }
        if (StringUtils.isNotEmpty(updatedLoginUserBuilder.getPassword())) {
            this.password = new BCryptPasswordEncoder().encode(updatedLoginUserBuilder.getPassword());
        } else {
            this.password = oldUser.getPassword();
        }
        this.createDate = oldUser.getCreateDate();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Column(name = "id", updatable = false)
    private Long id;

    @NotEmpty
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "nomdeplume")
    private String nomdeplume;

    @JsonIgnore
    @NotEmpty
    @Column(name = "password")
    private String password;

    @Column(name = "create_date")
    private LocalDateTime createDate;

}
