package com.moviebookingapp.registrationAndLogin.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.moviebookingapp.registrationAndLogin.model.User;

@Repository
public interface LoginRepository extends MongoRepository<User, String>{
	
	User findByUsername(String userName);

}
