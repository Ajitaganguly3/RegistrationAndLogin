package com.moviebookingapp.registrationAndLogin.model;

public class JWTResponse {
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JWTResponse(String token) {
		super();
		this.token = token;
	}

	public JWTResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

}
