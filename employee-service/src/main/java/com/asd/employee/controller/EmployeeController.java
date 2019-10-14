package com.asd.employee.controller;

import com.asd.employee.model.Employee;
import com.asd.employee.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeController(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @GetMapping("{id}")
    public Employee getOneEmployee(@PathVariable("id") Employee employee) {
        return employee;
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        if (employee.getHireDate().compareTo(employee.getFireDate()) > 0) {
            return null;
        }
        return employeeRepo.save(employee);
    }

    @PutMapping("{id}")
    public Employee updateEmployee(@PathVariable("id") Integer id, @RequestBody Employee newEmployee) {
        if (newEmployee.getHireDate().compareTo(newEmployee.getFireDate()) > 0) {
            return null;
        }

        return employeeRepo.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setDepartment(newEmployee.getDepartment());
            employee.setPosition(newEmployee.getPosition());
            employee.setHireDate(newEmployee.getHireDate());
            employee.setFireDate(newEmployee.getFireDate());
            return employeeRepo.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return employeeRepo.save(newEmployee);
        });
    }

    @DeleteMapping("{id}")
    public void deleteEmployee(@PathVariable("id") Integer id) {
        employeeRepo.deleteById(id);
    }
}
