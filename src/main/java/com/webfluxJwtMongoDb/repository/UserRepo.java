package com.webfluxJwtMongoDb.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.webfluxJwtMongoDb.model.User;

@Repository
public interface UserRepo extends ReactiveCrudRepository<User, String> {

	
}
