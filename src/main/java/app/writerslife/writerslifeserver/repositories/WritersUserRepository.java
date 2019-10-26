package app.writerslife.writerslifeserver.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import app.writerslife.writerslifeserver.models.entities.WritersUser;

@Component
public interface WritersUserRepository extends CrudRepository<WritersUser, Long> {
	
	@Query("SELECT u FROM WritersUser u WHERE u.username = ?1 and u.email = ?2")
	public Optional<WritersUser> findUser(String username, String email);	
	
	@Query("SELECT u FROM WritersUser u WHERE u.username = ?1")
	public Optional<WritersUser> findUserByUsername(String username);
	
	@Query("SELECT u FROM WritersUser u WHERE u.email = ?1")
	public Optional<WritersUser> findUserByEmail(String email);
}
