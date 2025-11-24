package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.model.Employee;
import com.pedroluizforlan.pontodoc.repository.EmployeeRespository;
import com.pedroluizforlan.pontodoc.service.EmployeeService;
import com.pedroluizforlan.pontodoc.service.exceptions.BusinessException;

@Service
public class EmployeeServiceImp implements EmployeeService{

    private final EmployeeRespository employeeRespository;

    public EmployeeServiceImp(EmployeeRespository employeeRespository){
        this.employeeRespository = employeeRespository;
    }

    @Override
    public List<Employee> findAll() {
       var employees = employeeRespository.findAll();
       return employees;
    }

    @Override
    public Employee findById(Long id) {
        return employeeRespository.findById(id).orElseThrow(() -> new RuntimeException("Collaborator not found with id: " + id));
    }

    @Override
    public Employee create(Employee employee) {
        employee.setCreatedAt(LocalDateTime.now());
        return employeeRespository.save(employee);
    }

    @Override
    public Employee update(Long id, Employee employee) {    
        Employee employeeToUpdate = findById(id);

        if (!Objects.equals(employee.getJobTitle(), employeeToUpdate.getJobTitle())) {
            employeeToUpdate.setJobTitle(employee.getJobTitle());
        }

        if (!Objects.equals(employee.getHiringDate(), employeeToUpdate.getHiringDate())) {
            employeeToUpdate.setHiringDate(employee.getHiringDate());
        }

        if (!Objects.equals(employee.getManagerId(), employeeToUpdate.getManagerId())) {
            if(employee.getManagerId().isLeadership()){
                throw new BusinessException("The "+ employee.getManagerId().getId()+" isn't a leadership employee");
            }

            employeeToUpdate.setManagerId(employee.getManagerId());
        }

        if (!Objects.equals(employee.getDepartment(), employeeToUpdate.getDepartment())) {
            employeeToUpdate.setDepartment(employee.getDepartment());
        }

        employeeToUpdate.setUpdatedAt(LocalDateTime.now());

        return employeeRespository.save(employeeToUpdate);
    }

    @Override
    public Employee delete(Long id) {
       Employee employee = findById(id);
       employee.setDeleatedAt(LocalDateTime.now());
       return employeeRespository.save(employee);
    }
    
}
