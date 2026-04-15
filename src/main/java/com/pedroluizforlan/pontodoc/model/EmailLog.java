package com.pedroluizforlan.pontodoc.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "email_log")
@Data
@Getter
@Setter
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "collaborator_id")
    private Collaborator collaborator;

    @Enumerated(EnumType.STRING)
    @Column(name="email_type")
    private EmailType emailType;
    
    @Column(name="email_subject")
    private String emailSubject;

    @Column(name="email_body")
    private String emailBody;

    @CreationTimestamp
    @Column(name = "send_at")
    private LocalDateTime sendAt;

    public enum EmailType{
        NEW_USER,SIGN_DOC
    }
}