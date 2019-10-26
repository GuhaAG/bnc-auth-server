package app.writerslife.writerslifeserver.models.builders;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WritersUserBuilder {
	
	//required parameters
	
    @Size(min=4, message="Username should be at least 4 characters long")
	private String username;	

    @Email
	private String email;
    
    @Size(min=8, max=20, message="Password should be between 8 and 20 characters")
    private String password;
		
	//optional parameters
    
	private String firstname;
	private String lastname;
	private String nomdeplume;	
	
}
