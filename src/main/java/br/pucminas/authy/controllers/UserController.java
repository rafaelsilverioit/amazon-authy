package br.pucminas.authy.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.authy.models.Login;
import br.pucminas.authy.models.User;
import br.pucminas.authy.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"Users"})
@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService service;
	
	// Using constructor instead of @AutoWired to ease unit testing.
	public UserController(UserService service) {
		this.service = service;
	}
	
	@ApiOperation(value = "Autentica um usuário identificado pelo seu e-mail e senha.")
	@RequestMapping(path="/auth", method=RequestMethod.POST)
	public User authenticate(
			@ApiParam(required = true, value = "Objeto do login.")
			@Valid
			@RequestBody
			Login login) {
		User user = new User();
		user.setEmail(login.getEmail());
		user.setPassword(login.getPassword());
		return service.authenticate(user);
	}
	
	@ApiOperation(value = "Registra um usuário.")
	@RequestMapping(method=RequestMethod.POST)
	public User create(
			@ApiParam(required = true, value = "Objeto do usuário.")
			@Valid
			@RequestBody
			User user) {
		return service.create(user);
	}
}
