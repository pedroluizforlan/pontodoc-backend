package com.pedroluizforlan.pontodoc.service;

import java.io.InputStream;

import com.pedroluizforlan.pontodoc.model.dto.StoredFile;

public interface FileStorageService {
    StoredFile save(InputStream inputStream, String fileName);

    InputStream get(String path);

    void delete(String path);
}
