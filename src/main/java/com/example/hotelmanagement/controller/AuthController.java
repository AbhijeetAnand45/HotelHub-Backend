package com.example.hotelmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelmanagement.dto.LoginRequest;
import com.example.hotelmanagement.dto.Response;
import com.example.hotelmanagement.entity.User;
import com.example.hotelmanagement.service.interfac.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private IUserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody User user) {
		Response response = userService.register(user);
		System.out.println("Response is " + response);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
		Response response = userService.login(loginRequest);
		System.out.println("Response is " + response);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

}
