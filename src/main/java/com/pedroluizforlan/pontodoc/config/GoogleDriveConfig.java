package com.pedroluizforlan.pontodoc.config;

import org.springframework.context.annotation.Configuration;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.services.drive.Drive;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


@Configuration
public class GoogleDriveConfig {
    @Value("${google.credentials.path}")
    private String credentialsFilePath;

    public Drive getDriveService() throws IOException, GeneralSecurityException {
        
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new ClassPathResource(credentialsFilePath).getInputStream())
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/drive"));

        HttpCredentialsAdapter requestInitializer = new HttpCredentialsAdapter(credentials);

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                com.google.api.client.json.gson.GsonFactory.getDefaultInstance(),
                requestInitializer
        )
        .setApplicationName("ponto-doc")
        .build();
    }

}
