package com.pedroluizforlan.pontodoc.service.imp;

import com.pedroluizforlan.pontodoc.model.dto.StoredFile;
import com.pedroluizforlan.pontodoc.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class S3FileStorageService implements FileStorageService {

    private final S3Client s3Client;
    private final String bucket;

    public S3FileStorageService(S3Client s3Client,
                                @Value("${s3.bucket}") String bucket) {
        this.s3Client = s3Client;
        this.bucket = bucket;
    }


    @Override
    public StoredFile save(InputStream inputStream, String fileName) {

        try {

            String key = buildKey();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            try (DigestInputStream dis = new DigestInputStream(inputStream, digest)) {
                dis.transferTo(buffer);
            }

            byte[] bytes = buffer.toByteArray();

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType("application/pdf")
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(bytes));

            String hash = bytesToHex(digest.digest());

            return new StoredFile(key, hash);

        } catch (Exception e) {
            log.error("LOG DE ERRO: ",e);
            throw new RuntimeException("Erro ao salvar no S3", e);
        }
    }

    private String buildKey() {
        LocalDate now = LocalDate.now();

        return String.format(
                "documents/%d/%02d/%s.pdf",
                now.getYear(),
                now.getMonthValue(),
                UUID.randomUUID()
        );
    }

    private String bytesToHex(byte[] hashBytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public InputStream get(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        return s3Client.getObject(request);
    }

    @Override
    public void delete(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }
}
