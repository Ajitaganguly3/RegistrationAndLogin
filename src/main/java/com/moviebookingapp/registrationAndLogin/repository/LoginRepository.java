package com.moviebookingapp.registrationAndLogin.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.moviebookingapp.registrationAndLogin.model.User;

@Component
public interface LoginRepository extends MongoRepository<User, String>{
	
	//User findByUsername(String username);
	
    Optional<User> findByUsername(String username);


}
