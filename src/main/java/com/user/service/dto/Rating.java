package com.user.service.dto;

import java.util.Date;

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
	private Long userId;
	private Long hotelId;
	private Float rating;
	private Date reviewAt;	
	private String feedback;
	private Hotel hotel;

}
