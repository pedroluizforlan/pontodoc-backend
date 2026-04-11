package com.pedroluizforlan.pontodoc.service.imp;

import com.pedroluizforlan.pontodoc.model.SupportTicketConversation;
import com.pedroluizforlan.pontodoc.service.SupportTicketConversationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportTicketConversationServiceImp implements SupportTicketConversationService {
    @Override
    public List<SupportTicketConversation> findAll() {
        return List.of();
    }

    @Override
    public SupportTicketConversation findById(Long aLong) {
        return null;
    }

    @Override
    public SupportTicketConversation create(SupportTicketConversation entity) {
        return null;
    }

    @Override
    public SupportTicketConversation update(Long aLong, SupportTicketConversation entity) {
        return null;
    }

    @Override
    public SupportTicketConversation delete(Long aLong) {
        return null;
    }
}
