package com.asd.employee.controller;

import com.asd.employee.exception.DepartmentNotFoundException;
import com.asd.employee.model.Department;
import com.asd.employee.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("department")
public class DepartmentController {
    private DepartmentRepo departmentRepo;

    @Autowired
    public DepartmentController(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    @GetMapping("{id}")
    public Department getOneDepartment(@PathVariable("id") Integer id) {
        return departmentRepo.findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentRepo.save(department);
    }

    @PutMapping("{id}")
    public Department updateDepartment(@PathVariable("id") Integer id, @RequestBody Department newDepartment) {
        return departmentRepo.findById(id).map(department -> {
            department.setName(newDepartment.getName());
            return departmentRepo.save(department);
        }).orElseGet(() -> {
            newDepartment.setId(id);
            return departmentRepo.save(newDepartment);
        });
    }

    @DeleteMapping("{id}")
    public void deleteDepartment(@PathVariable("id") Integer id) {
        departmentRepo.deleteById(id);
    }
}
