package com.pedroluizforlan.pontodoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedroluizforlan.pontodoc.model.Employee;

@Repository
public interface EmployeeRespository extends JpaRepository<Employee, Long>{
    
}
