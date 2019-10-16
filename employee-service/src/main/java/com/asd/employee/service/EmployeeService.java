package com.asd.employee.service;

import com.asd.employee.exception.DepartmentNotFoundException;
import com.asd.employee.exception.EmployeeNotFoundException;
import com.asd.employee.exception.IncorrectHireFireDateException;
import com.asd.employee.exception.PositionNotFoundException;
import com.asd.employee.model.Department;
import com.asd.employee.model.Employee;
import com.asd.employee.model.Position;
import com.asd.employee.repo.DepartmentRepo;
import com.asd.employee.repo.EmployeeRepo;
import com.asd.employee.repo.PositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    void checkEmployeeCorrectness(Employee employee) {
        LocalDate hireDate = employee.getHireDate();
        LocalDate fireDate = employee.getFireDate();
        Department department = employee.getDepartment();
        Position position = employee.getPosition();

        if (hireDate == null || fireDate == null || employee.getHireDate().compareTo(employee.getFireDate()) > 0) {
            throw new IncorrectHireFireDateException();
        }

        if (department == null) {
            throw new DepartmentNotFoundException(0);
        } else {
            int departmentId = department.getId();
            departmentRepo.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        }

        if (position == null) {
            throw new PositionNotFoundException(0);
        } else {
            int positionId = position.getId();
            positionRepo.findById(positionId).orElseThrow(() -> new PositionNotFoundException(positionId));
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee getOneEmployee(Integer id) {
        return employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee createEmployee(Employee newEmployee) {
        checkEmployeeCorrectness(newEmployee);
        return employeeRepo.save(newEmployee);
    }

    public Employee updateEmployee(Integer id, Employee newEmployee) {
        checkEmployeeCorrectness(newEmployee);

        return employeeRepo.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setDepartment(newEmployee.getDepartment());
            employee.setPosition(newEmployee.getPosition());
            employee.setHireDate(newEmployee.getHireDate());
            employee.setFireDate(newEmployee.getFireDate());
            return employeeRepo.save(employee);
        }).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public void deleteEmployee(Integer id) {
        employeeRepo.deleteById(id);
    }
}
