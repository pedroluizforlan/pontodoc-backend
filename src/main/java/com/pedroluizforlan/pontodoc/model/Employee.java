package com.pedroluizforlan.pontodoc.model;


import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "employees")
@Data
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="job_title")
    private String jobTitle;

    private String department;

    private String pis;

    @Column(name = "hiring_date")
    private LocalDate hiringDate;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "manager_id", nullable = true)
    private Employee managerId;

    //alterar o diagrama entidade relacionamento
    private boolean leadership;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deleatedAt;
}
