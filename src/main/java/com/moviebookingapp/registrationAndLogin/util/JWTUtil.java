package com.moviebookingapp.registrationAndLogin.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@PropertySource(value = {"classpath:application.properties"})
@Service
public class JWTUtil {
	
	private static final long serialVersionUID = -2550185165626007485L;
	public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 24;
	
	@Value("${jwt.secret}")
	private String secret;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	//retrieve loginId from jwt token
	public String getloginIdFromToken(String token) {
		return getClaimFromToken(token, Claims :: getSubject);
	}
	
	//retrieving expiration date from jwt token
	public Date getExpirationDate(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	//parse and retrieve the claims from JWT token
	private Claims getAllClaimsFromToken(String token) {
		
		return Jwts.parser().setSigningKey("java").parseClaimsJws(token).getBody();
	}
	
	//generating token for user
	public String generateToken(UserDetails userDetails) {
		
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}
	
	//creating token
	private String createToken(Map<String, Object> claims, String subject) {
		
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, "java").compact();
	}
	
	//checking if the token is expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDate(token);
		return expiration.before(new Date());
	}
	
	//checking if the token is valid
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getloginIdFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
