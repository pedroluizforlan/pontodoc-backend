package com.pedroluizforlan.pontodoc.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "signature_request")
@Data
@Getter
@Setter
public class SignatureRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @Column(name="document_hr")
    private List<DocumentHR> documentHR;

    @ManyToOne
    private Collaborator collaborator;

    private String token;

    @Column(name="expired_at")
    private LocalDateTime expiredAt;

    @Column(name="used_at")
    private LocalDateTime usedAt;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

}
