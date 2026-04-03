package com.pedroluizforlan.pontodoc.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.List;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import com.pedroluizforlan.pontodoc.model.DocumentsBatch;
import com.pedroluizforlan.pontodoc.model.DocumentsBatch.Status;

public class DocumentsUtils {

    public static String saveFileAndCalculateHash(InputStream inputStream, Path targetPath) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        try (DigestInputStream dis = new DigestInputStream(inputStream, digest)) {
            Files.copy(dis, targetPath);
        }

        byte[] hashBytes = digest.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static String extractTextFromDocument(PDDocument document) throws IOException {
        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            return textStripper.getText(document);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public static List<PDDocument> creatingDocumentList(PDDocument pdDocument) throws IOException {
        Splitter splitter = new Splitter();

        try {
            List<PDDocument> listOfPdDocuments = splitter.split(pdDocument);
            return listOfPdDocuments;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    public static DocumentsBatch createDocumentsBatchObject(MultipartFile multipartFile) {
        Path path = Paths.get("/var/documentos/" + multipartFile.getOriginalFilename());

        String hash;
        try {
            hash = DocumentsUtils.saveFileAndCalculateHash(multipartFile.getInputStream(), path);

            DocumentsBatch batch = new DocumentsBatch();

            batch.setName(multipartFile.getOriginalFilename());
            batch.setOriginalHash(hash);
            batch.setStatus(Status.RECIVED);

            return batch;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
