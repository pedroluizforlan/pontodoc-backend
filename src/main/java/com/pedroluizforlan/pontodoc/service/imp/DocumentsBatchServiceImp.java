package com.pedroluizforlan.pontodoc.service.imp;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pedroluizforlan.pontodoc.model.DocumentsBatch;
import com.pedroluizforlan.pontodoc.model.DocumentsBatch.Status;
import com.pedroluizforlan.pontodoc.repository.DocumentsBatchRepository;
import com.pedroluizforlan.pontodoc.service.Crud;
import com.pedroluizforlan.pontodoc.service.DocumentService;
import com.pedroluizforlan.pontodoc.util.DocumentsUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentsBatchServiceImp implements DocumentService, Crud<Long, DocumentsBatch> {

    private final DocumentsBatchRepository documentsBatchRepository;
    private final CollaboratorServiceImp collaboratorService;

    @Override
    public List<DocumentsBatch> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public DocumentsBatch findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public DocumentsBatch create(DocumentsBatch entity) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
        // return documentsBatchRepository.save();
    }

    @Override
    public DocumentsBatch update(Long id, DocumentsBatch entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public DocumentsBatch delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void storeDocument(MultipartFile multipartFile) {
        try {

            // Primeira etapa é salvar no banco o Batch de documentos;
            var documentBatch = DocumentsUtils.createDocumentsBatchObject(multipartFile);

            // DocumentsBatch createdBatch = this.create(documentBatch);

            // Loader é a classe que carrega um multipartFile e transforma em um PDFDocument
            // da Bibl. PDFBox
            List<PDDocument> listOfPdDocuments = DocumentsUtils
                    .creatingDocumentList(Loader.loadPDF(multipartFile.getBytes()));
            List<String> activesCollaborators = this.getListOfActivesCollaborators();

            this.processArchives(activesCollaborators, listOfPdDocuments);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private List<String> getListOfActivesCollaborators() {
        return this.collaboratorService.getAllNamesOfActivesCollaborators();
    }

    private void processArchives(List<String> collaboratorsNames, List<PDDocument> listOfArchives) {
        try {

            List<PDDocument> arquivosSemDonos = new ArrayList<>();
            List<PDDocument> comDono = new ArrayList<>();
            log.info("--- INICIANDO PROCESSO ---");
            for (PDDocument splitDoc : listOfArchives) {

                PDFTextStripper stripper = new PDFTextStripper(); String textoDaPagina;
                
            
                textoDaPagina = stripper.getText(splitDoc);

                for (String collaborator: collaboratorsNames){
                    if(textoDaPagina.contains(collaborator)){
                        System.out.println("--- DONO ENCONTRADO ---");
                        System.out.println(textoDaPagina.substring(0, 25));
                        System.out.println(collaborator);
                        comDono.add(splitDoc);
                    }else{ 
                        arquivosSemDonos.add(splitDoc);
                    }
                }
                
    
                splitDoc.close();
            }
            log.info("--- ACABOU O PROCESSO ---\"");
            log.info("RESULTADO - AQV COM DONO: {} - AQV SEM DONO: {}", comDono.size(), arquivosSemDonos.size());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
