package com.springboot.sharework.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.google.api.client.util.Value;
import com.google.firebase.auth.FirebaseAuthException;
import com.springboot.sharework.config.service.AuthenServiceImpl;
import com.springboot.sharework.payload.RegisterDto;
import com.springboot.sharework.payload.SignInRequest;
import com.springboot.sharework.payload.SignInResponse;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthContronller {

	@Value("${firebase.apiKey}")
    private String apiKey;
	
	private AuthenServiceImpl authenService;
	
	public AuthContronller(AuthenServiceImpl authenService) {
		this.authenService = authenService;
	}
	
	
	@GetMapping
	public ResponseEntity<String> index(){
		
		String response = "SHAREWORK HELLO!!";
		
		return new  ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<String> createUser(@RequestBody RegisterDto registerDto) throws FirebaseAuthException{
		
		String response = authenService.createUser(registerDto);
		
		return new  ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	
	@PostMapping(value = "/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request) {
		
		SignInResponse response = authenService.login(request);
        return ResponseEntity.ok(response);
    }
}
