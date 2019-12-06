package app.writerslife.server.models.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import app.writerslife.server.models.models.LoginUserBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "LoginUsers", schema = "public")
public class LoginUser {
	
	public LoginUser() {}
	
	public LoginUser(LoginUserBuilder loginUserBuilder) {
		this.username = loginUserBuilder.getUsername();
		this.email = loginUserBuilder.getEmail();
		this.firstname = loginUserBuilder.getFirstname();
		this.lastname = loginUserBuilder.getLastname();
		this.nomdeplume = loginUserBuilder.getNomdeplume();
		this.password = new BCryptPasswordEncoder().encode(loginUserBuilder.getPassword());
		this.createDate = LocalDateTime.now();	
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	@Column(name = "Id",updatable = false)	
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
	
	@Column(name="create_date")
    private LocalDateTime createDate;	
		
}
