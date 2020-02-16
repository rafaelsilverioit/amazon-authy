package br.pucminas.authy.controllers;

import br.pucminas.authy.models.Login;
import br.pucminas.authy.models.User;
import br.pucminas.authy.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
			Login login)
		throws Throwable {
		User user = new User();
		user.setEmail(login.getEmail());
		user.setPassword(login.getPassword());

		return service.authenticate(user);
	}
	
	@ApiOperation(value = "Busca um usuário.")
	@RequestMapping(path="/{id}", method=RequestMethod.GET)
	public User user(
			@ApiParam(required = true, value = "UUID do usuário.")
			@Valid
			@PathVariable("id")
			String id)
		throws Throwable {
		return service.user(id);
	}
	
	@ApiOperation(value = "Registra um usuário.")
	@RequestMapping(method=RequestMethod.POST)
	public User create(
			@ApiParam(required = true, value = "Objeto do usuário.")
			@Valid
			@RequestBody
			User user)
		throws Throwable {
		return service.create(user);
	}
}
