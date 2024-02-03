package com.user.service.service;

import java.util.List;

import com.user.service.dto.UserDto;
import com.user.service.entity.UserEntity;

public interface UserService {

	public UserEntity getUserById(Long userId);
	public List<UserEntity> getAllUser();
	public UserEntity addUser(UserDto userdto) throws Exception;
	public UserEntity updateUser(Long userId , UserDto userDto) throws Exception;
	public String deleteUser(Long userId);
	
	
	
}
