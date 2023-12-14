package com.algowebpro.electronic.store.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

	private String userId;

	@Size(min = 3, max = 20, message = "Invalid Name!!")
	private String name;

	@Email(message = "Invalid User Email !!")
	// @Pattern(regexp =
	// "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n",message = "Invalid user
	// email !!")
	@NotBlank(message = "Email is Required !!")
	private String email;

	@NotBlank(message = "Password is required !!")
	private String password;

	@Size(min = 4, max = 6, message = "Invalid Gender !!")
	private String gender;

	@NotBlank(message = "write something yourself !!")
	private String about;

	// Custom validator
	// @Pattern

	private String imageName;

}
