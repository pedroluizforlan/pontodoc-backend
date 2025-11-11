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
@Table(name = "persons")
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

    @Column(name="cellphone_number",nullable=false, unique=true, length=11)
    private String number;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deleatedAt;
}
