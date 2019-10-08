package br.pucminas.authy.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.Returns;
import org.mockito.internal.stubbing.answers.ReturnsArgumentAt;

import br.pucminas.authy.controllers.UserController;
import br.pucminas.authy.models.Login;
import br.pucminas.authy.models.User;
import br.pucminas.authy.services.UserService;

class UserControllerTest {
	private final UserService service = Mockito.mock(UserService.class);
	
	private UserController controller;
	
	private final static User mockedUser = new User(UUID.randomUUID().toString(), "Rafael", "Amaral", "contato@realngnx.tv", new Date(), "hashed", new Date());
	
	@BeforeEach
	void initUseCase() {
		controller = new UserController(service);
		
		when(service.authenticate(any(User.class))).then(new Returns(mockedUser));
		when(service.create(any(User.class))).then(new ReturnsArgumentAt(0));
	}
	
	@Test
	void loginTest() {
		Login login = new Login("contato@realngnx.tv", "abcdef");
		User user = controller.authenticate(login);
		
		assertEquals(user.getName(), "Rafael");
		assertEquals(user.getLastname(), "Amaral");
		assertEquals(user.getEmail(), login.getEmail());
		assertEquals(user.getPassword(), "hashed");
	}
	
	@Test
	void registrationTest() {
		User createdUser = controller.create(mockedUser);
		
		assertEquals(createdUser.getName(), "Rafael");
		assertEquals(createdUser.getLastname(), "Amaral");
		assertEquals(createdUser.getEmail(), mockedUser.getEmail());
		assertEquals(createdUser.getPassword(), "hashed");
	}
}
