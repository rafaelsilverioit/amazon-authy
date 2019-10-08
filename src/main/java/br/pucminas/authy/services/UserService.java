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
	
	public User authenticate(User user) {
		Optional<User> maybeUser = this.users
			.stream()
			.filter(u -> {
				String email = user.getEmail();
				String password = user.getPassword();
				String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();

				boolean matchesEmail = !StringUtils.isEmpty(email) && u.getEmail().equalsIgnoreCase(email);
				boolean matchesPassword = !StringUtils.isEmpty(hashedPassword) && u.getPassword().equalsIgnoreCase(hashedPassword);
				
				return matchesEmail && matchesPassword;
			})
			.findFirst();
		
		if(maybeUser.isPresent()) {
			return maybeUser.get();
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
	}
	
	public User create(User user) {
		Optional<User> maybeUser = this.users
			.stream()
			.filter(u -> {
				String email = user.getEmail();
				boolean matchesEmail = !StringUtils.isEmpty(email) && u.getEmail().equalsIgnoreCase(email);
				
				return matchesEmail;
			})
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
}
