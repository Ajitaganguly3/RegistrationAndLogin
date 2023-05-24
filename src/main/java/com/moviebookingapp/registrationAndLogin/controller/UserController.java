package com.moviebookingapp.registrationAndLogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.registrationAndLogin.dto.UserProfileDTO;
import com.moviebookingapp.registrationAndLogin.model.JWTResponse;
import com.moviebookingapp.registrationAndLogin.model.User;
import com.moviebookingapp.registrationAndLogin.service.UserProfileService;
import com.moviebookingapp.registrationAndLogin.service.UserProfileServiceImpl;
import com.moviebookingapp.registrationAndLogin.util.JWTUtil;

@RestController
public class UserController {

	
	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
	UserProfileServiceImpl userProfileServiceImpl;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private UserDetails userDetails;
	
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping("/register")
	public String createUserProfile(@RequestBody UserProfileDTO userProfileDTO) {
		
		String response = userProfileService.CreateUserProfile(userProfileDTO);

		return response;
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> generateToken(@RequestBody User authenticationRequest) throws Exception {

		System.out.println("Start generateToken");
		System.out.println(authenticationRequest.getUserName() + " " + authenticationRequest.getPassword());
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUserName(), authenticationRequest.getPassword()));

		} catch (UsernameNotFoundException e) {
			throw new Exception("Incorrect username or password", e);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		userDetails = userProfileServiceImpl.loadUserByUsername(authenticationRequest.getUserName());

		String token = jwtUtil.generateToken(userDetails);

		System.out.println("JWT Token: " + token);
		
		System.out.println("end generateToken");

		return ResponseEntity.ok(new JWTResponse(token));
	}
	


	@PostMapping("/validate")
	public String validateToken(@RequestBody JWTResponse response) {
		System.out.println("start validateToken");
		String token=response.getToken();
		System.out.println(token);
		System.out.println("End validateToken");
		//System.out.println(userDetails.getUsername());
		boolean res= jwtUtil.validateToken(token, userDetails);
		if(res) {
			return userDetails.getUsername()+" "+res;
		}
		else {
			return ""+res;
		}
	}

}
