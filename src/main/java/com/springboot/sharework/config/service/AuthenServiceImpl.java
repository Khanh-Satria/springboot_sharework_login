package com.springboot.sharework.config.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.springboot.sharework.payload.RegisterDto;
import com.springboot.sharework.payload.SignInRequest;
import com.springboot.sharework.payload.SignInResponse;

@Service
public class AuthenServiceImpl {

	
	
	public String createUser(RegisterDto registerDto) throws FirebaseAuthException {
		
		CreateRequest request = new CreateRequest()
			    .setEmail(registerDto.getEmail())
			    .setEmailVerified(false)
			    .setPassword(registerDto.getPassword())
			    .setPhoneNumber(registerDto.getPhoneNumber())
			    .setDisplayName(registerDto.getDisplayName())
			    .setDisabled(false);

			UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
			
			return "Successfully created new user: " + userRecord.getUid();
	}
	
	
	public String findUserByEmail(String email) throws FirebaseAuthException {
		
		UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
		if(userRecord != null) {
			return "Successfully fetched user data: " + userRecord.getEmail();
		}
		
		return " Not successfully fetched user data: " + email;

	}
	
	
	public SignInResponse login(SignInRequest request) {
		
		
		
		try {
			UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(request.getEmail());
		} catch (FirebaseAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		  // Gửi yêu cầu POST đến API Firebase Authentication
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", request.getEmail());
        params.add("password", request.getPassword());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBgNaF9xUh4tpiZV7kqZd6xd3B12ca98fQ",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        String responseBody = responseEntity.getBody();

        // Parse token từ phản hồi
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String token = jsonObject.get("idToken").getAsString();

        // Trả về token
        SignInResponse response = new SignInResponse(token);
        
        return response;
	}
}
