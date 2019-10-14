package com.asd.employee.repo;

import com.asd.employee.model.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
}
