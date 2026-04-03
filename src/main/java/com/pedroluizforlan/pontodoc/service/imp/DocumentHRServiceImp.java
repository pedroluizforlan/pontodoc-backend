package com.pedroluizforlan.pontodoc.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pedroluizforlan.pontodoc.model.DocumentHR;
import com.pedroluizforlan.pontodoc.service.Crud;
import com.pedroluizforlan.pontodoc.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DocumentHRServiceImp implements Crud<Long,DocumentHR>{
    
    @Override
    public List<DocumentHR> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public DocumentHR findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public DocumentHR create(DocumentHR entity) {
         // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public DocumentHR update(Long id, DocumentHR entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public DocumentHR delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    // @Override
    // public void storeDocument(MultipartFile multipartFile) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'storeDocument'");
    // }

    
    
}



            
        //     for (PDDocument splitDoc : listOfPdDocuments) {
            
        //         PDFTextStripper stripper = new PDFTextStripper();
        //         System.out.println("--- Conteúdo da Página ---");
        //         String textoDaPagina = stripper.getText(splitDoc);
                
        //         System.out.println(textoDaPagina);
        //         System.out.println("--------------------------");
        //         // if(textoDaPagina.substring(0, 100).contains("RECIBO DE VALE TRANSPORTE")){
        //         // //     System.out.println("--- Conteúdo da Página ---");
        //         // // System.out.println(textoDaPagina);
        //         // // System.out.println("--------------------------");
                    
        //         //     listOfRecibosTransporte.add(splitDoc);
        //         // } else if(textoDaPagina.substring(0, 100).contains("RECIBO DE VALE REFEIÇÃO")){
        //         // //                         System.out.println("--- Conteúdo da Página ---");
        //         // // System.out.println(textoDaPagina);
        //         // // System.out.println("--------------------------");
                    
        //         //     listOfRecibosAlimentacao.add(splitDoc);
        //         // } else if(textoDaPagina.substring(0, 100).contains("Recibo de Pagamento de Salário")){
        //         // //                         System.out.println("--- Conteúdo da Página ---");
        //         // // System.out.println(textoDaPagina);
        //         // // System.out.println("--------------------------");
                    
        //         //     listOfHolerites.add(splitDoc);
        //         // } else if(textoDaPagina.substring(0, 100).contains("DADOS DO EMPREGADOR")){
        //         // //                         System.out.println("--- Conteúdo da Página ---");
        //         // // System.out.println(textoDaPagina);
        //         // // System.out.println("--------------------------");
                    
        //         // //     listOfCartãoPonto.add(splitDoc);
        //         // }
        //         // else{

        //         // //                         System.out.println("--- Conteúdo da Página ---");
        //         // // System.out.println(textoDaPagina);
        //         // // System.out.println("--------------------------");
        //         //     // System.out.println("NÃO SEI QUEM É");
        //         //     deuErrado.add(splitDoc);
        //         // }
        //         System.out.println("--------------------------");


        //         splitDoc.close();
        // }
                // System.out.println(listOfRecibosAlimentacao.size());
                // System.out.println(listOfRecibosTransporte.size());
                // System.out.println(deuErrado.size());







