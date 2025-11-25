package com.pedroluizforlan.pontodoc.service.integrations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.util.Collections;
import com.pedroluizforlan.pontodoc.config.GoogleDriveConfig;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class GoogleDriveService {
    
    private final GoogleDriveConfig googleDriveConfig;

    
    @Value("${google.drive.root-folder-id}")
    private String rootFolderId;

    public String createFolderForCollaborator(String collaboratorName) {
        try {
            
            Drive drive = googleDriveConfig.getDriveService();

            
            File metadata = new File();
            metadata.setName(collaboratorName);
            metadata.setMimeType("application/vnd.google-apps.folder");
            metadata.setParents(Collections.singletonList(rootFolderId));

            
            File folder = drive.files().create(metadata)
                    .setFields("id")  
                    .execute();

            return folder.getId();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar pasta no Google Drive", e);
        }
    }
}