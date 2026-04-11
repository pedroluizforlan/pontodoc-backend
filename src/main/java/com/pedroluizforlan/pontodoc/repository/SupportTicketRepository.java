package com.pedroluizforlan.pontodoc.repository;

import com.pedroluizforlan.pontodoc.model.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket,Long> {


}
