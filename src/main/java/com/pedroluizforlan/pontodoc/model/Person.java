package com.pedroluizforlan.pontodoc.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "person")
@Data
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private LocalDate birthday;

    @Column(nullable=false, unique=true, length=11)
    private String cpf;

    private String address;

    @Column(nullable=false, length=8)
    private String cep;

    @Column(length=10)
    private String gender;

    @Column(name="number",nullable=false, unique=true, length=11)
    private String cellphoneNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Column(name = "deleated_at")
    private LocalDateTime deleatedAt;
}
