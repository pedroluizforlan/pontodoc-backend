package com.pedroluizforlan.pontodoc.service.imp;

import com.pedroluizforlan.pontodoc.model.DocumentHR;
import com.pedroluizforlan.pontodoc.model.SignatureRequest;
import com.pedroluizforlan.pontodoc.repository.SignatureRequestRepository;
import com.pedroluizforlan.pontodoc.service.SignatureRequestService;
import com.pedroluizforlan.pontodoc.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignatureRequestServiceImp implements SignatureRequestService {

    private final SignatureRequestRepository signatureRequestRepository;

    @Override
    public List<SignatureRequest> findAll() {
        return List.of();
    }

    @Override
    public SignatureRequest findById(Long aLong) {
        return null;
    }

    @Override
    public SignatureRequest create(SignatureRequest entity) {
        try {
            String hashToken = TokenUtils.encrypt(entity.getToken());
            System.out.println(entity.getToken());
            System.out.println(hashToken);
            entity.setExpiredAt(LocalDateTime.now().plusDays(2L));
            entity.setToken(hashToken);

            return signatureRequestRepository.save(entity);
        } catch (Exception e){
            log.error("Erro na criação de um novo token de assinatura");
            throw new RuntimeException();
        }
    }

    @Override
    public SignatureRequest update(Long aLong, SignatureRequest entity) {
        return null;
    }

    @Override
    public SignatureRequest delete(Long aLong) {
        return null;
    }

    private Boolean validatorToken(SignatureRequest signatureRequest, String token){
        try {
            return TokenUtils.validateToken(token, signatureRequest.getToken());

        }catch (Exception e){
            System.out.println("TOKEN NÃO VALIDO");
            return  false;
        }
    }

    public List<String> getDocumentsToSign(String token){
        SignatureRequest sr = signatureRequestRepository.findUnusedTokens(PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .orElseThrow(() ->  new RuntimeException("NÃO há token valido"));

        if(!this.validatorToken(sr, token)){
            //construir errp
            return null;
        }


        return sr.getDocumentHR().stream().map(DocumentHR::getPath).toList();
    }
}
