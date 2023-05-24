package com.moviebookingapp.registrationAndLogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.moviebookingapp.registrationAndLogin.filter.JWTAuthenticationFilter;
import com.moviebookingapp.registrationAndLogin.service.UserProfileServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	
	@Autowired
	UserProfileServiceImpl userProfileServiceImpl;
	
	@Autowired
	JWTAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public DefaultSecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception{
		 http.csrf().disable()
		 		.cors().disable()
				.authorizeRequests()
				.antMatchers("/register", "/login").permitAll()
				.antMatchers("/validate", "/swagger-ui.html", "/swagger-ui/**", "/v2/api-docs").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/user/**").hasRole("USER")
				.anyRequest().authenticated()
				.and()
				.exceptionHandling()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userProfileServiceImpl);
	}
	 
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	

}
