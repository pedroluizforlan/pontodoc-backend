package com.pedroluizforlan.pontodoc.service.imp;

import com.pedroluizforlan.pontodoc.model.SupportTicket;
import com.pedroluizforlan.pontodoc.repository.SupportTicketRepository;
import com.pedroluizforlan.pontodoc.service.SupportTickectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SupportTicketServiceImp implements SupportTickectService {

    private final SupportTicketRepository supportTicketRepository;


    @Override
    public List<SupportTicket> findAll() {
        return supportTicketRepository.findAll();
    }

    @Override
    public SupportTicket findById(Long aLong) {
        return supportTicketRepository
                .findById(aLong)
                .orElseThrow(() -> new RuntimeException("Elemento n encontrado"));
    }

    @Override
    public SupportTicket create(SupportTicket entity) {
        return this.create(entity);
    }

    @Override
    public SupportTicket update(Long aLong, SupportTicket entity) {
        var supportToUpdate = this.findById(aLong);

        supportToUpdate.setStatus(entity.getStatus());

        return supportTicketRepository.save(supportToUpdate);
    }

    @Override
    public SupportTicket delete(Long aLong) {
        var supportToUpdate = this.findById(aLong);

        supportToUpdate.setDeletedAt(LocalDateTime.now());

        return supportTicketRepository.save(supportToUpdate);
    }
}
