package com.pedroluizforlan.pontodoc.service;

import com.pedroluizforlan.pontodoc.model.SignatureRequest;

import java.util.List;

public interface SignatureRequestService extends Crud<Long,SignatureRequest> {


    List<String> getDocumentsToSign(String token);
}
