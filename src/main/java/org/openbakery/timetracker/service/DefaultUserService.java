package org.openbakery.timetracker.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.openbakery.timetracker.data.Role;
import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.persistence.Persistence;
import org.openbakery.timetracker.service.exception.LoginFailedException;
import org.openbakery.timetracker.util.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;

public class DefaultUserService implements UserService {
	private static Logger log = LoggerFactory.getLogger(DefaultUserService.class);

	@Autowired
	private Persistence persistence;

	public DefaultUserService() {
	}

	@PostConstruct
	public void initialize() {
		// create first user if no user is found;
		if (!hasUsers()) {
			log.debug("no user in DB found so create admin user");
			User user = new User();
			user.setName("admin");
			user.setPassword(PasswordEncoder.encode("admin"));
			user.setRole(Role.ADMINISTRATOR);
			store(user);
			log.debug("admin user created with password admin");
		}

	}

	public User login(String username, String password) throws LoginFailedException {
        String passwordHash = PasswordEncoder.encode(password);

		ImmutableMap<String, Object> parameters = new ImmutableMap.Builder<String, Object>().put("username", username)
				.put("password", passwordHash).build();

		List<User> result = persistence.query("Select user from User as user where user.name = :username and user.password = :password AND user.disabled = false",
				parameters, User.class);

		if (result.size() != 1) {
			throw new LoginFailedException("User '" + username + "' not found");
		}
		User user = result.get(0);
		user.setAuthenticated(true);

		return user;
	}

	public void logoff(User user) {
		user.setAuthenticated(false);
	}

	private boolean hasUsers() {
		Number number = persistence.querySingle("Select count(user.id) from User user", Number.class);
		return (number.longValue() > 0);
	}

	public void store(User user) {
		persistence.store(user);
	}

	public void delete(User user) {
		persistence.delete(user);
	}

	public List<User> getAllUsers() {
		return persistence.query("Select user from User as user", User.class);
	}


    public List<User> getAllActiveUsers() {
		return persistence.query("Select user from User as user WHERE user.disabled = false", User.class);
	}

}
