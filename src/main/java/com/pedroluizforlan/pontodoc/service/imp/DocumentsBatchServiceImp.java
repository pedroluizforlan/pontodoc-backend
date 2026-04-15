package com.pedroluizforlan.pontodoc.service.imp;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.pedroluizforlan.pontodoc.service.*;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.DocumentHR;
import com.pedroluizforlan.pontodoc.model.DocumentsBatch;
import com.pedroluizforlan.pontodoc.model.DocumentHR.Attribution;
import com.pedroluizforlan.pontodoc.model.DocumentsBatch.Status;
import com.pedroluizforlan.pontodoc.model.dto.StoredFile;
import com.pedroluizforlan.pontodoc.repository.DocumentsBatchRepository;
import com.pedroluizforlan.pontodoc.util.DocumentsUtils;
import com.pedroluizforlan.pontodoc.util.TextUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentsBatchServiceImp implements DocumentService, Crud<Long, DocumentsBatch> {

    private final DocumentHRService documentHRService;
    private final DocumentsBatchRepository documentsBatchRepository;
    private final CollaboratorService collaboratorService;
    private final FileStorageService fileStorageService;

    @Override
    public List<DocumentsBatch> findAll() {
        return documentsBatchRepository.findAll();
    }

    @Override
    public DocumentsBatch findById(Long id) {
        return documentsBatchRepository.getReferenceById(id);
    }

    @Override
    public DocumentsBatch create(DocumentsBatch entity) {
        return documentsBatchRepository.save(entity);
    }

    @Override
    public DocumentsBatch update(Long id, DocumentsBatch entity) {
        DocumentsBatch batchToUpdate = this.findById(id);

        batchToUpdate.setName(entity.getName());
        batchToUpdate.setStatus(entity.getStatus());

        return documentsBatchRepository.save(batchToUpdate);
    }

    @Override
    public DocumentsBatch delete(Long id) {
        var batch = this.findById(id);
        batch.setDeletedAt(LocalDateTime.now());
        return documentsBatchRepository.save(batch);
    }

    @Override
    public void storeDocument(MultipartFile multipartFile) {
        try {

            // Primeira etapa é salvar no banco o Batch de documentos e salvar o pdf;
            StoredFile storedFile = this.fileStorageService.save(multipartFile.getInputStream(),
                    multipartFile.getOriginalFilename());

            var documentBatch = DocumentsUtils.createDocumentsBatchObject(multipartFile, storedFile);

            DocumentsBatch createdBatch = this.create(documentBatch);

            // Loader é a classe que carrega um multipartFile e transforma em um PDFDocument
            // da Bibl. PDFBox

            // this.processBatchAsync(activesCollaborators, listOfPdDocuments,
            // createdBatch);


            this.processBatchAsync(createdBatch, multipartFile.getBytes());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Async
    public void processBatchAsync(DocumentsBatch batch, byte[] bytes) {
        log.info(batch.toString());
        try (PDDocument document = Loader.loadPDF(bytes)) {

            List<PDDocument> listOfPdDocuments = DocumentsUtils.creatingDocumentList(document);

            List<Collaborator> collaborators = getListOfActivesCollaborators();

            batch.setStatus(Status.PROCESSING);
            batch = this.update(batch.getId(), batch);

            processArchives(collaborators, listOfPdDocuments, batch);

            batch.setStatus(Status.FINISHED);
            this.update(batch.getId(), batch);

        } catch (Exception e) {
            batch.setStatus(Status.ERROR);
            this.update(batch.getId(), batch);
            log.error("Erro [processBatchAsync]: ", e);
        }
    }

    private List<Collaborator> getListOfActivesCollaborators() {
        try {
            return this.collaboratorService.getAllNamesOfActivesCollaborators();
        } catch (Exception e) {
            log.error("O Erro está aqui amigão [getListOfActivesCollaborators]: ", e);
            return null;
        }

    }

    private void processArchives(List<Collaborator> collaboratorsNames, List<PDDocument> listOfArchives,
            DocumentsBatch batch) {
        try {

            List<PDDocument> arquivosSemDonos = new ArrayList<>();
            List<PDDocument> comDono = new ArrayList<>();
            log.info("--- INICIANDO PROCESSO ---");

            for (PDDocument splitDoc : listOfArchives) {

                PDFTextStripper stripper = new PDFTextStripper();
                String textoDaPagina;
                var text = stripper.getText(splitDoc);
                textoDaPagina = TextUtils.normalize(text);



                boolean found = false;
                StoredFile storedFile = this.fileStorageService.save(DocumentsUtils.pdDocumentToInputStream(splitDoc),
                        "");

                for (Collaborator collaborator : collaboratorsNames) {

                    String name = collaborator.getPerson().getName();
                    var cpf = collaborator.getPerson().getCpf();
                    if (textoDaPagina.contains(name)) {

                        found = true;
                        DocumentHR hr = DocumentsUtils.createDocumentsHrObject(batch, collaborator, storedFile,
                                text, Attribution.SUCCESS);
                          
                        var createdHr = this.documentHRService.create(hr);
                        break;
                    } 
                    
                    if (textoDaPagina.contains(cpf)){ 
                        found = true;
                        DocumentHR hr = DocumentsUtils.createDocumentsHrObject(batch, collaborator, storedFile,
                                text, Attribution.SUCCESS);
                          
                        var createdHr = this.documentHRService.create(hr);
                        break;
                    }

                    var nameWithLessCaracteres = name.length() > 20 ? name.substring(0, 20) : name;
                    if (textoDaPagina.contains(nameWithLessCaracteres)) {
                        log.warn("Match parcial encontrado para: {}", nameWithLessCaracteres);
                        DocumentHR hr = DocumentsUtils.createDocumentsHrObject(batch, collaborator, storedFile,
                                text, Attribution.ANALYSIS);
                        found = true;
                        var createdHr = this.documentHRService.create(hr);
                        break;
                    }
                }

                if (found) {
                    comDono.add(splitDoc);
                } else {
                    log.info("TEXTO DA PAGINA: {}", textoDaPagina.substring(0, 300));
                     DocumentHR hr = DocumentsUtils.createDocumentsHrObject(batch ,storedFile,
                                text);
                        found = true;
                        var createdHr = this.documentHRService.create(hr);
                    arquivosSemDonos.add(splitDoc);
                }

                splitDoc.close();
            }
            log.info("--- ACABOU O PROCESSO ---\"");
            log.info("RESULTADO - AQV COM DONO: {} - AQV SEM DONO: {}", comDono.size(), arquivosSemDonos.size());
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}
