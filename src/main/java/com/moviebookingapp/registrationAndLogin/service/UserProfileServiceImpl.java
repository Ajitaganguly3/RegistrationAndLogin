package com.moviebookingapp.registrationAndLogin.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moviebookingapp.registrationAndLogin.model.User;
import com.moviebookingapp.registrationAndLogin.repository.LoginRepository;

@Service
public class UserProfileServiceImpl implements UserDetailsService {

	
	private LoginRepository loginRepository;
	
	public UserProfileServiceImpl(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          User user = loginRepository.findByUsername(username)
                 .orElseThrow(() ->
                         new UsernameNotFoundException("User not found with username or email: "+ username));

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);
    }
}
