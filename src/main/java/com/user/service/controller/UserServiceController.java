package com.user.service.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.config.CustomUserDetail;
import com.user.service.dto.ResponseDto;
import com.user.service.dto.UserDto;
import com.user.service.entity.UserEntity;
import com.user.service.repository.UserRepository;
import com.user.service.service.JwtService;
import com.user.service.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserServiceController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;

	@GetMapping("/all")
	public ResponseEntity<List<UserEntity>> getAllUser() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
	}

	@GetMapping("/{userId}")
	@CircuitBreaker(name = "ratingHotelBreaker" , fallbackMethod = "ratingHotelFallback")
//	@Retry(name = "ratingHotelRetry" , fallbackMethod = "ratingHotelFallback")
//	@RateLimiter(name = "ratingHotelRateLimit" , fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<?> getUserById(@PathVariable Long userId) {
		
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
	}
	
	public ResponseEntity<?> ratingHotelFallback(Long userId , Exception ex){
		
		return ResponseEntity.badRequest().body("Something went wrong! try after some time");
	}

	@PostMapping("/register")
	public ResponseEntity<?> addUser(@RequestBody @Valid UserDto userDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(userDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.builder().message(e.getMessage())
					.status(HttpStatus.BAD_REQUEST).timestamp(new Date()).build());
		}
	}
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtService service;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(){
		authManager.authenticate(new UsernamePasswordAuthenticationToken("vala@gmail.com", "1234"));
		UserEntity user = userRepo.findByEmail("vala@gmail.com").get();
		return ResponseEntity.ok(service.generateToken(new CustomUserDetail(user)));
	}

	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable(name = "userId") Long userId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, userDto));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.builder().message(e.getMessage())
					.status(HttpStatus.BAD_REQUEST).timestamp(new Date()).build());
		}
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userId));
	}

}
