package com.springboot.sharework.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


@Configuration
public class FirebaseConfig {

	@Autowired
    private Environment env;
 
	
	
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
    	InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");
 
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(env.getProperty("firebase.databaseUrl"))
                .build();
 
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
 
        return FirebaseApp.getInstance();
    }
}
