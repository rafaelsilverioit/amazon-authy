package br.pucminas.authy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@JsonIgnore
	private String id;

	@NotBlank
	private String name;

	@NotBlank
	private String lastName;

	@NotBlank @Email
	private String email;

	@NotNull
	private Date dob;
	
	@NotBlank @Size(min = 6, max = 8)
	private String password;
	
	@JsonIgnore
	private Date createdAt;

	@JsonProperty
	public String getId() {
		return id;
	}

	@JsonIgnore
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty
	public Date getCreatedAt() {
		return createdAt;
	}

	@JsonIgnore
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
