package br.pucminas.authy.models;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;

@ApiModel
public class User {
	private String id;

	@NotBlank
	private String name;

	@NotBlank
	private String lastname;

	@NotBlank @Email
	private String email;

	@NotNull
	private Date dob;
	
	@NotBlank @Size(min = 6, max = 8)
	private String password;
	
	private Date createdAt;

	public User() {
	}

	public User(String id, String name, String lastname, String email, Date dob, String password, Date createdAt) {
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.dob = dob;
		this.password = password;
		this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}

	@JsonIgnore
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@JsonIgnore
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
