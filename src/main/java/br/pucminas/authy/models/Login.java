package br.pucminas.authy.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
	@NotBlank @Email
	private String email;
	
	@NotBlank @Size(min = 6, max = 8)
	private String password;
}
