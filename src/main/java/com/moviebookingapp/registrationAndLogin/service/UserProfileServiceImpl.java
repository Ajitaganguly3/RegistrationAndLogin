package com.moviebookingapp.registrationAndLogin.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moviebookingapp.registrationAndLogin.repository.LoginRepository;

@Service
public class UserProfileServiceImpl implements UserDetailsService {

	@Autowired
	private LoginRepository loginRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		System.out.println("Start loadUserByUsername");

		com.moviebookingapp.registrationAndLogin.model.User user = loginRepository.findByUsername(userName);

		if (user == null) {

			System.out.println("User not found:" + userName);
			throw new UsernameNotFoundException("User not found !!");
		}
		System.out.println("User found: " + user.getUserName());
		
		System.out.println("end loadUserByUsername");

		return new User(user.getUserName(), user.getPassword(), new ArrayList<>());
	}
}
