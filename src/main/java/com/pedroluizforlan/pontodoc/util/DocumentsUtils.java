package com.pedroluizforlan.pontodoc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.DocumentHR;
import com.pedroluizforlan.pontodoc.model.DocumentHR.Attribution;
import com.pedroluizforlan.pontodoc.model.DocumentHR.DocumentType;
import com.pedroluizforlan.pontodoc.model.DocumentsBatch;
import com.pedroluizforlan.pontodoc.model.DocumentsBatch.Status;
import com.pedroluizforlan.pontodoc.model.dto.StoredFile;

public class DocumentsUtils {

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

    public static DocumentsBatch createDocumentsBatchObject(MultipartFile multipartFile, StoredFile stFile) {
        try {
            DocumentsBatch batch = new DocumentsBatch();
            batch.setName(multipartFile.getOriginalFilename());
            batch.setOriginalHash(stFile.hash());
            batch.setStatus(Status.RECIVED);
            batch.setPath(stFile.path());

            return batch;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DocumentHR createDocumentsHrObject(DocumentsBatch batch,
            Collaborator collaborator, StoredFile stFile, String text, Attribution att) {
        try {
            DocumentHR hr = new DocumentHR();
            hr.setDocument_batch(batch);
            hr.setCollaborator(collaborator);
            hr.setPath(stFile.path());
            hr.setDocumentHash(stFile.hash());
            hr.setStatus(com.pedroluizforlan.pontodoc.model.DocumentHR.Status.WAITING);
            hr.setExtractedText(text);
            hr.setDocumentType(getDocumentType(text));
            hr.setAttribution(att);

            return hr;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

        public static DocumentHR createDocumentsHrObject(DocumentsBatch batch, StoredFile stFile, String text) {
        try {
            DocumentHR hr = new DocumentHR();
            hr.setDocument_batch(batch);
            hr.setPath(stFile.path());
            hr.setDocumentHash(stFile.hash());
            hr.setStatus(com.pedroluizforlan.pontodoc.model.DocumentHR.Status.WAITING);
            hr.setExtractedText(text);
            hr.setDocumentType(getDocumentType(text));
            hr.setAttribution(Attribution.ERROR);

            return hr;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream pdDocumentToInputStream(PDDocument document) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        document.save(outputStream);

        document.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private static DocumentType getDocumentType(String text) {
        if (text.substring(0, 100).contains("RECIBO DE VALE TRANSPORTE")) {
            return DocumentType.TRANSPORT_RECEIPT;
        } else if (text.substring(0, 100).contains("RECIBO DE VALE REFEIÇÃO")) {
            return DocumentType.FOOD_RECEIPT;
        } else if (text.substring(0, 100).contains("Recibo de Pagamento de Salário")) {
            return DocumentType.PAY_STUB;
        } else if (text.substring(0, 100).contains("DADOS DO EMPREGADOR")) {
            return DocumentType.TIME_CARD;
        } else {
            return DocumentType.OTHER;
        }
    }
}