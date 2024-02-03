package com.user.service.externalService;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.service.dto.Rating;

@FeignClient("RATING-SERVICE")
public interface RatingService {
	
	@GetMapping("/api/rating/all/user/{userId}")
	List<Rating> getRatingByuserId(@PathVariable Long userId);
	

}
