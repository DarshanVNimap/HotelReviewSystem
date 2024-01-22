package com.user.service.service;

import java.util.List;

import com.user.service.dto.UserDto;

public interface UserService {

	public UserDto getUserById(Long userId);
	public List<UserDto> getAllUser();
	public UserDto addUser(UserDto userdto) throws Exception;
	public UserDto updateUser(Long userId , UserDto userDto) throws Exception;
	public String deleteUser(Long userId);
	
	
	
}
