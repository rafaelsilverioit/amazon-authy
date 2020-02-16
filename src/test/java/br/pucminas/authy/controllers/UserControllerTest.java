package br.pucminas.authy.controllers;

import br.pucminas.authy.models.Login;
import br.pucminas.authy.models.User;
import br.pucminas.authy.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.Returns;
import org.mockito.internal.stubbing.answers.ReturnsArgumentAt;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {
	private final UserService service = Mockito.mock(UserService.class);
	
	private UserController controller;
	
	private final static User mockedUser = new User(
		UUID.randomUUID().toString(),
		"Rafael",
		"Amaral",
		"contato@realngnx.tv",
		new Date(),
		"hashed",
		new Date()
	);
	
	@BeforeEach
	void initUseCase()
		throws Throwable {
		controller = new UserController(service);
		
		when(service.authenticate(any(User.class))).then(new Returns(mockedUser));
		when(service.create(any(User.class))).then(new ReturnsArgumentAt(0));
	}
	
	@Test
	void loginTest()
		throws Throwable {
		Login login = new Login("contato@realngnx.tv", "abcdef");
		User user = controller.authenticate(login);
		
		assertEquals(user.getName(), "Rafael");
		assertEquals(user.getLastName(), "Amaral");
		assertEquals(user.getEmail(), login.getEmail());
		assertEquals(user.getPassword(), "hashed");
	}
	
	@Test
	void registrationTest()
		throws Throwable {
		User createdUser = controller.create(mockedUser);
		
		assertEquals(createdUser.getName(), "Rafael");
		assertEquals(createdUser.getLastName(), "Amaral");
		assertEquals(createdUser.getEmail(), mockedUser.getEmail());
		assertEquals(createdUser.getPassword(), "hashed");
	}
}
