package com.user.service.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.dto.ResponseDto;
import com.user.service.dto.UserDto;
import com.user.service.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserServiceController {

	@Autowired
	private UserService userService;

	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> getAllUser() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserById(Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
	}

	@PostMapping("/add")
	public ResponseEntity<?> addUser(UserDto userDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(userDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.builder().message(e.getMessage())
					.status(HttpStatus.BAD_REQUEST).timestamp(new Date()).build());
		}
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
	public ResponseEntity<?> deleteUserById(Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userId));
	}

}
