//package com.pedroluizforlan.pontodoc.service.imp;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.security.DigestInputStream;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.UUID;
//
//import org.springframework.stereotype.Service;
//
//import com.pedroluizforlan.pontodoc.model.dto.StoredFile;
//import com.pedroluizforlan.pontodoc.service.FileStorageService;
//
//@Service
//public class LocalFileStorageService implements FileStorageService {
//
//    private final Path rootLocation;
//
//    public LocalFileStorageService() {
//        this.rootLocation = Paths.get("/var/documentos");
//    }
//
//    @Override
//    public StoredFile save(InputStream inputStream, String fileName) {
//
//        try {
//            Files.createDirectories(rootLocation);
//
//            String safeFileName = UUID.randomUUID() + ".pdf";
//            Path targetPath = rootLocation.resolve(safeFileName);
//
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//
//            try (DigestInputStream dis = new DigestInputStream(inputStream, digest)) {
//                Files.copy(dis, targetPath, StandardCopyOption.REPLACE_EXISTING);
//            }
//
//            byte[] hashBytes = digest.digest();
//
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hashBytes) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1)
//                    hexString.append('0');
//                hexString.append(hex);
//            }
//
//            return new StoredFile(targetPath.toString(),hexString.toString());
//
//        } catch (IOException | NoSuchAlgorithmException e){
//            throw new RuntimeException("Erro ao salvar arquivo", e);
//        }
//    }
//
//    @Override
//    public InputStream get(String path) {
//        try {
//            return Files.newInputStream(Paths.get(path));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void delete(String path) {
//        try {
//            Files.deleteIfExists(Paths.get(path));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
