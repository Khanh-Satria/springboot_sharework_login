package com.springboot.sharework.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

	private String email;
	private String password;
	private String phoneNumber;
	private String displayName;
}
