package com.moviebookingapp.registrationAndLogin.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.moviebookingapp.registrationAndLogin.model.Role;

public interface RoleRepository  extends MongoRepository<Role, Long>{
	
	Optional<Role> findByName(String name);
}
