package com.pedroluizforlan.pontodoc.repository;

import com.pedroluizforlan.pontodoc.model.DocumentSinged;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentSignedRepository extends JpaRepository<DocumentSinged, Long> {
}
