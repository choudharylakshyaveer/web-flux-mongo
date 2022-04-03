package com.webfluxJwtMongoDb.rest;

import com.webfluxJwtMongoDb.model.User;
import com.webfluxJwtMongoDb.security.JWTUtil;
import com.webfluxJwtMongoDb.security.PBKDF2Encoder;
import com.webfluxJwtMongoDb.security.model.AuthRequest;
import com.webfluxJwtMongoDb.security.model.AuthResponse;
import com.webfluxJwtMongoDb.service.UserService;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 *
 * @author ard333
 */
@RestController
public class AuthenticationREST {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private PBKDF2Encoder passwordEncoder;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
		Mono<User> a = userService.findByUsername(ar.getUsername());
		User block;
		try {
			block = a.toFuture().get();
			System.out.println(block);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userService.findByUsername(ar.getUsername()).map((userDetails) -> {
			System.out.println("ar.getPassword(): " + ar.getPassword());
			System.out.println("ar.getUsername(): " + ar.getUsername());
			if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
				System.out.println("Logged in successfully");
				return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails)));

			} else {
				System.out.println("Failed to login");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

}
