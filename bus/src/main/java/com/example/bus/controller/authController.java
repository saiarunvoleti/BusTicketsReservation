package com.example.bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bus.dto.UserCredentialsDTO;
import com.example.bus.dto.UserDetailsDTO;
import com.example.bus.entity.UserDetailsEntity;
import com.example.bus.security.JwtUtil;
import com.example.bus.service.userDetailsService;

import jakarta.validation.Valid;

@RestController
public class authController {
	@Autowired
	userDetailsService userService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@PostMapping("/auth/login")
	public ResponseEntity<String> createUser(@Valid @RequestBody UserCredentialsDTO userCredentials)
	{
		System.out.println("In controller");
		
		//get userdetails
		UserDetailsEntity userDetails = userService.getUserByEmail(userCredentials.getMailId());
		if(userDetails == null)
		{
			ResponseEntity<String> res = new ResponseEntity<>("User is not found",HttpStatus.NOT_FOUND);
			return res;
		}
		
		//now check if passwords matched
		boolean isPasswordsMatched = userService.validatePassword(userCredentials.getPassword(), userDetails.getPassword());
		if(!isPasswordsMatched)
		{
			ResponseEntity<String> res = new ResponseEntity<>("Forbidden Error",HttpStatus.FORBIDDEN);
			return res;
		}
		
		//generate token using password
		String token = jwtUtil.generateToken(userDetails.getMailId(), userDetails.getUserType());
		
		ResponseEntity<String> res = new ResponseEntity<>(token,HttpStatus.OK);
		return res;
	}
	
	@PostMapping("/auth/register")
	public ResponseEntity<String> createUser(@Valid @RequestBody UserDetailsDTO userDetails)
	{
		System.out.println("In controller");
		UserDetailsEntity userDetailsEntity = userService.getUserByEmail(userDetails.getMailId());
		if(userDetailsEntity != null)
		{
			ResponseEntity<String> res = new ResponseEntity<>("User is already present",HttpStatus.NOT_FOUND);
			return res;
		}
		userService.saveUser(userDetails);
		
		//generate token using password
		userDetailsEntity = userService.getUserByEmail(userDetails.getMailId());
		String token = jwtUtil.generateToken(userDetailsEntity.getMailId(), userDetailsEntity.getUserType());
		
		ResponseEntity<String> res = new ResponseEntity<>(token,HttpStatus.CREATED);
		return res;
	}
}
