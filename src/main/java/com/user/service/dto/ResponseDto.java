package com.user.service.dto;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ResponseDto {
	
	private String message;
	private HttpStatus status;
	private Date timestamp;

}
