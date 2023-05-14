package com.springboot.sharework.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {

	private String email;
    private String password;
}
