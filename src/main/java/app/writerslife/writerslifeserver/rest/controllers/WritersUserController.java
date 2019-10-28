package app.writerslife.writerslifeserver.rest.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.writerslife.writerslifeserver.models.builders.WritersUserBuilder;
import app.writerslife.writerslifeserver.models.entities.WritersUser;
import app.writerslife.writerslifeserver.services.WritersUserManagementService;


@RestController
@RequestMapping("/users")
public class WritersUserController {
	
 	@Autowired
	private WritersUserManagementService service; 	
 	
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
	public WritersUser add(@Valid @RequestBody WritersUserBuilder user) throws DuplicateUserException {    	
    	WritersUser addUser = new WritersUser(user);
    	
    	if(service.findUser(user.getUsername(),user.getEmail()).isPresent()) {
    		throw new DuplicateUserException();
    	}
    	
    	return service.addUser(addUser);    	
    }
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)        
	public WritersUser getByUserId(@PathVariable Long id) throws UserNotExistsException {    	
    	Optional<WritersUser> user = service.findUser(id);
    	
    	if(!user.isPresent()) {
    		throw new UserNotExistsException();    	
    	}
    	
    	return user.get();    	
    }
    
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
	public WritersUser update(@PathVariable Long id,@Valid @RequestBody WritersUserBuilder user) throws UserNotExistsException {
    	Optional<WritersUser> thisUser = service.findUser(id);
    	
    	if(!thisUser.isPresent()) {
    		throw new UserNotExistsException();    	
    	}
    	
    	WritersUser updateduser = new WritersUser(user);
		updateduser.setId(id);
		
		return service.addUser(updateduser);
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
}


