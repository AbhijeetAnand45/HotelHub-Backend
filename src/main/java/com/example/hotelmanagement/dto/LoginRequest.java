package com.example.hotelmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
	
	@NotBlank(message = "email is required")
	private String email;
	@NotBlank(message = "password is required")
	private String password;
}
