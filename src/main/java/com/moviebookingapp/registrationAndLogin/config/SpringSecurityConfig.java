package com.moviebookingapp.registrationAndLogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	
	 private UserDetailsService userDetailsService;

	    public void SecurityConfig(UserDetailsService userDetailsService){
	        this.userDetailsService = userDetailsService;
	    }

	    @Bean
	    public static PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(
	                                 AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }

	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http.csrf().disable()
	                .authorizeHttpRequests((authorize) ->
	                        //authorize.anyRequest().authenticated()
	                        authorize.antMatchers("/register","/login").permitAll()
	                                .antMatchers("/validate", "/swagger-ui.html", "/swagger-ui/**", "/v2/api-docs").permitAll()
	                                .anyRequest().authenticated()

	                );

	        return http.build();
	    }
	
	/*@Bean
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
	*/
	

}
