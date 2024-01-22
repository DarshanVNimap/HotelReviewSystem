package com.user.service.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.service.dto.UserDto;
import com.user.service.entity.UserEntity;
import com.user.service.exception.UserNotFoundException;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;
import com.user.service.util.ErrorMessageConstant;
import com.user.service.util.SuccessMessageConstant;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto getUserById(Long userId) {
		UserEntity getUser = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorMessageConstant.USER_NOT_FOUND));
		return modelMapper.map(getUser , UserDto.class);
	}

	@Override
	public List<UserDto> getAllUser() {
		return userRepo.findAll()
				       .stream().map(
				    		   user -> modelMapper.map(user, UserDto.class)
				        ).toList();
	}

	@Override
	public UserDto addUser(UserDto userdto) throws Exception {
		UserEntity user = modelMapper.map(userdto, UserEntity.class);
		if(userRepo.save(user) == null) {
			throw new Exception(ErrorMessageConstant.SOMETHING_WENT_WRONG);
		}
		return userdto;
	}

	@Override
	public UserDto updateUser(Long userId, UserDto userDto) throws Exception {
		UserEntity user  = modelMapper.map(userDto, UserEntity.class);
		user.setId(userId);
		if(userRepo.save(user) == null) {
			throw new Exception(ErrorMessageConstant.SOMETHING_WENT_WRONG);
		}
		return userDto;
	}

	@Override
	public String deleteUser(Long userId) {
		userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorMessageConstant.USER_NOT_FOUND));
		userRepo.deleteById(userId);
		return SuccessMessageConstant.USER_DELETE;
	}

}
