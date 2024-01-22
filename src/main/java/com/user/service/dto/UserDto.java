package com.user.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
	
	@NotBlank(message = "please enter name")
	private String name;
	
	@NotBlank(message = "please enter email")
	@Email(message = "please enter valid email")
	private String email;
	
	@Size(max = 255 , message = "about max length should be 255 character")
	private String about;

}
