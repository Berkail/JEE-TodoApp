package org.myTaskApp.Services;

import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;
import org.myTaskApp.Entities.User;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserService {
	@PersistenceContext
	private EntityManager em;

	public User createUser(String username, String email, String password) {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		user.setCreationDt(LocalDateTime.now());
		user.setLastConnDt(LocalDateTime.now());
		em.persist(user);
		return user;

	}

	public User authenticate(String username, String password) {
		try {
			User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.deletionDt IS NULL",
					User.class).setParameter("username", username).getSingleResult();

			if (BCrypt.checkpw(password, user.getPassword())) {
				user.setLastConnDt(LocalDateTime.now());
				em.merge(user);
				return user;
			}
		} catch (Exception e) {
		}
		return null;
	}

	public void deleteUser(User user) {
		user.setDeletionDt(LocalDateTime.now());
		em.merge(user);
	}
}