package br.pucminas.authy.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import br.pucminas.authy.models.User;

@Service
public class UserService {
	private final List<User> users = new ArrayList<>();
	
	// TODO: Move authentication related code to LoginService.
	public User authenticate(User user) {
		Optional<User> maybeUser = this.users
			.stream()
			.filter(u -> isSameEmailAndPassword(user, u))
			.findFirst();
		
		return getUserOrThrowException(maybeUser);
	}
	
	public User user(String id) {
		Optional<User> maybeUser = this.users
			.stream()
			.filter(u -> isMatchingUser(id, u))
			.findFirst();
		
		return getUserOrThrowException(maybeUser);
	}
	
	public User create(User user) {
		Optional<User> maybeUser = this.users
			.stream()
			.filter(u -> isSameEmail(user, u))
			.findFirst();
		
		if(maybeUser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists!");
		}
		
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
		boolean matchesEmail = !StringUtils.isEmpty(email) && user.getEmail().equalsIgnoreCase(email);
		
		return matchesEmail;
	}
	
	private Boolean isMatchingUser(String id, User user) {
		return user.getId().equalsIgnoreCase(id);
	}
	
	private User getUserOrThrowException(Optional<User> maybeUser) {
		if(maybeUser.isPresent()) {
			return maybeUser.get();
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
	}
}
