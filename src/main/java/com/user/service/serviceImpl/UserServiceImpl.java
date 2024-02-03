package com.user.service.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.dto.Hotel;
import com.user.service.dto.Rating;
import com.user.service.dto.UserDto;
import com.user.service.entity.UserEntity;
import com.user.service.exception.UserNotFoundException;
import com.user.service.externalService.HotelService;
import com.user.service.externalService.RatingService;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;
import com.user.service.util.ErrorMessageConstant;
import com.user.service.util.SuccessMessageConstant;

@Service
public class UserServiceImpl implements UserService {
	
//	private static String RATING_BASE_URL = "http://RATING-SERVICE/api/rating";
//	private static String HOTEL_BASE_URL = "http://HOTEL-SERVICE/api/hotel/";
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RatingService ratingService;
	
//	@Autowired
//	private RestTemplate restTemplate;
	

	@Override
	public UserEntity getUserById(Long userId) {
		UserEntity getUser = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorMessageConstant.USER_NOT_FOUND));
//		Rating[] getAllRating = restTemplate.getForObject(RATING_BASE_URL+"/all/user/"+userId , Rating[].class);
//		List<Rating> asList = Arrays.asList(getAllRating);
		List<Rating> allRating = ratingService.getRatingByuserId(userId);
		List<Rating> rate = allRating.stream().map(rating -> {
//			ResponseEntity<Hotel> hotel =  restTemplate.getForEntity(HOTEL_BASE_URL+rating.getHotelId(), Hotel.class);
			Hotel hotel = hotelService.getHotelById(rating.getHotelId());
			rating.setHotel(hotel);
			return rating ;
		})
		.toList();
		getUser.setRating(rate);
		return getUser;
	}

	@Override
	public List<UserEntity> getAllUser() {
		return userRepo.findAll();
	}

	@Override
	public UserEntity addUser(UserDto userdto) throws Exception {
		UserEntity user = modelMapper.map(userdto, UserEntity.class);
		user.setPassword(encoder.encode(userdto.getPassword()));
		if(userRepo.save(user) == null) {
			throw new Exception(ErrorMessageConstant.SOMETHING_WENT_WRONG);
		}
		return user;
	}
	
	

	@Override
	public UserEntity updateUser(Long userId, UserDto userDto) throws Exception {
		UserEntity user  = modelMapper.map(userDto, UserEntity.class);
		user.setId(userId);
		if(userRepo.save(user) == null) {
			throw new Exception(ErrorMessageConstant.SOMETHING_WENT_WRONG);
		}
		return user;
	}

	@Override
	public String deleteUser(Long userId) {
		userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorMessageConstant.USER_NOT_FOUND));
		userRepo.deleteById(userId);
		return SuccessMessageConstant.USER_DELETE;
	}

}
