package com.asd.employee.controller;

import com.asd.employee.model.Employee;
import com.asd.employee.repo.EmployeeRepo;
import com.asd.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("{id}")
    public Employee getOne(@PathVariable("id") Integer id) {
        return employeeService.getOneEmployee(id);
    }

    @PostMapping
    public Employee create(@RequestBody Employee newEmployee) {
        return employeeService.createEmployee(newEmployee);
    }

    @PutMapping("{id}")
    public Employee update(@PathVariable("id") Integer id, @RequestBody Employee newEmployee) {
        return employeeService.updateEmployee(id, newEmployee);
    }

    @DeleteMapping("{id}")
    public void deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.deleteEmployee(id);
    }
}
