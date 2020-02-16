package br.pucminas.authy.services;

import br.pucminas.authy.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class UserService {
	private final List<User> users = new ArrayList<>();

	private final ResponseStatusException userNotFoundException =
			new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");

	// TODO: Move authentication related code to LoginService.
	public User authenticate(User user)
		throws Throwable {
		Optional<User> maybeUser = this.users
			.stream()
			.filter(u -> isSameEmailAndPassword(user, u))
			.findFirst();

		return maybeUser.orElseThrow(() -> {
			throw userNotFoundException;
		});
	}

	public User user(String id)
		throws Throwable {
		Optional<User> maybeUser = this.users
			.stream()
			.filter(u -> isMatchingUser(id, u))
			.findFirst();

		return maybeUser.orElseThrow(() -> {
			throw userNotFoundException;
		});
	}
	
	public User create(User user)
		throws Throwable {
		this.users
			.stream()
			.filter(u -> isSameEmail(user, u))
			.findFirst()
			.ifPresent(existingUser -> {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists!");
			});
		
		byte[] bytes = user.getPassword().getBytes();
		String hashedPassword = DigestUtils.md5DigestAsHex(bytes).toUpperCase();
		
		user.setId(UUID.randomUUID().toString());
		user.setCreatedAt(new Date());
		user.setPassword(hashedPassword);
		users.add(user);
		
		return user;
	}

	private Boolean isSameEmailAndPassword(User target, User user) {
		String email = target.getEmail();
		String password = target.getPassword();
		String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();

		boolean matchesEmail = !StringUtils.isEmpty(email) && user.getEmail().equalsIgnoreCase(email);
		boolean matchesPassword = !StringUtils.isEmpty(hashedPassword) && user.getPassword().equalsIgnoreCase(hashedPassword);
		
		return matchesEmail && matchesPassword;
	}
	
	private Boolean isSameEmail(User target, User user) {
		String email = target.getEmail();

		return !StringUtils.isEmpty(email) && user.getEmail().equalsIgnoreCase(email);
	}
	
	private Boolean isMatchingUser(String id, User user) {
		return user.getId().equalsIgnoreCase(id);
	}
}
