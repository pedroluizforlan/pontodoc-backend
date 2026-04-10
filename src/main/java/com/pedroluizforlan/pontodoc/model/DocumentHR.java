package com.pedroluizforlan.pontodoc.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "document_hr")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentHR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "documents_batch_id", nullable = false)
    private DocumentsBatch document_batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collaborator_id", nullable = true)
    private Collaborator collaborator;

    @Enumerated(EnumType.STRING)
    @Column(name="document_type")
    private DocumentType documentType;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "document_hash", length = 64, nullable = false, unique = true)
    private String documentHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;


    @Basic(fetch = FetchType.LAZY)
    @Column(name = "extracted_text", columnDefinition = "TEXT")
    private String extractedText;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "attribution")
    private Attribution attribution;

    @Column(name = "drive_url")
    private String driveUrl;

    public enum DocumentType {
        TIME_CARD,FOOD_RECEIPT,TRANSPORT_RECEIPT,PAY_STUB,OTHER
    }

    public enum Status {
        WAITING,VALIDATED,ERROR
    }

    public enum Attribution {
        SUCCESS, ERROR, ANALYSIS 
    }


}
