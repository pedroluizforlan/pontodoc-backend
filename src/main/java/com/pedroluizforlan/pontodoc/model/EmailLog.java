package com.pedroluizforlan.pontodoc.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name="email_type")
    private String emailType;
    
    @Column(name="email_subject")
    private String emailSubject;

    @Column(name="email_body")
    private String emailBody;

    @Column(name = "send_at")
    private LocalDateTime sendAt;
}