package app.writerslife.writerslifeserver.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.writerslife.writerslifeserver.models.entities.WritersUser;
import app.writerslife.writerslifeserver.repositories.WritersUserRepository;

@Component
public class WritersUserManagementService{

	@Autowired
	private WritersUserRepository repository;
	
	public WritersUser addUser(WritersUser user) {
		return repository.save(user);
	}
	
	public Optional<WritersUser> findUser(long id) {		
		return repository.findById(id);
	}
	
	public Optional<WritersUser> findUser(String username, String email) {		
		return repository.findUser(username, email);
	}

	public void deleteUser(long userId) {
		repository.deleteById(userId);
	}	
}