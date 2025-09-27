package com.pedroluizforlan.pontodoc.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users")
@Data
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType useType;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "uploaded_date")
    private LocalDateTime uploadedDate;

    @Column(name = "deleated_date")
    private LocalDateTime deleatedDate;

    // Getters and Setters

    public enum UserType {
        MANAGER,ASSESSOR,EMPLOYEE;
    }
}
