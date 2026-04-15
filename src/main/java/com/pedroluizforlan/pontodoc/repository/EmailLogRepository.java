package com.pedroluizforlan.pontodoc.repository;

import com.pedroluizforlan.pontodoc.model.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailLogRepository extends JpaRepository<EmailLog,Long> {
}
