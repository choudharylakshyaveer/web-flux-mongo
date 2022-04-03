package com.webfluxJwtMongoDb.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webfluxJwtMongoDb.model.User;
import com.webfluxJwtMongoDb.service.UserService;

import reactor.core.publisher.Mono;

@RestController
public class ControllerRest {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	public Mono<User>  saveUser(@RequestBody User user){
		Mono<User> a = userService.saveUser(user);
		System.out.println(user);
		return a;
		
	}
}
