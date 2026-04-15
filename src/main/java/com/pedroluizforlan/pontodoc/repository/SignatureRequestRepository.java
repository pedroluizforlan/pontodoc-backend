package com.pedroluizforlan.pontodoc.repository;

import com.pedroluizforlan.pontodoc.model.SignatureRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignatureRequestRepository extends JpaRepository<SignatureRequest,Long> {

    @Query("""
    SELECT sr FROM SignatureRequest sr
    WHERE sr.usedAt IS NULL 
        AND sr.expiredAt > CURRENT_TIMESTAMP
    ORDER BY sr.createdAt DESC
""")
    List<SignatureRequest> findUnusedTokens(Pageable pageable);
}
