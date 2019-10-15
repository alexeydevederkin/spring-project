package com.asd.employee.service;

import com.asd.employee.exception.DepartmentNotFoundException;
import com.asd.employee.exception.EmployeeNotFoundException;
import com.asd.employee.exception.PositionNotFoundException;
import com.asd.employee.model.Employee;
import com.asd.employee.repo.DepartmentRepo;
import com.asd.employee.repo.EmployeeRepo;
import com.asd.employee.repo.PositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepo employeeRepo;
    private DepartmentRepo departmentRepo;
    private PositionRepo positionRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo, DepartmentRepo departmentRepo, PositionRepo positionRepo) {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
        this.positionRepo = positionRepo;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee getOneEmployee(Integer id) {
        return employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee createEmployee(Employee newEmployee) {
        int departmentId = newEmployee.getDepartment().getId();
        departmentRepo.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));

        int positionId = newEmployee.getPosition().getId();
        positionRepo.findById(positionId).orElseThrow(() -> new PositionNotFoundException(positionId));

        if (newEmployee.getHireDate().compareTo(newEmployee.getFireDate()) > 0) {
            return null;
        }

        return employeeRepo.save(newEmployee);
    }

    public Employee updateEmployee(Integer id, Employee newEmployee) {
        int departmentId = newEmployee.getDepartment().getId();
        departmentRepo.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));

        int positionId = newEmployee.getPosition().getId();
        positionRepo.findById(positionId).orElseThrow(() -> new PositionNotFoundException(positionId));

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

    public void deleteEmployee(Integer id) {
        employeeRepo.deleteById(id);
    }
}
