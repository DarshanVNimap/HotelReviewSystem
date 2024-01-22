package com.user.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rating {
	
	private Long id;
	private Long ratingId;
	private Long userId;
	private Float rating;
	private String feedback;

}
