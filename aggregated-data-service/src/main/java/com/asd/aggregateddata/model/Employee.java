package com.asd.aggregateddata.model;

import java.time.LocalDate;

public class Employee {
    private Integer id;

    private String name;

    private Position position;

    private Department department;

    private LocalDate hireDate;

    private LocalDate fireDate;

    public Employee() {
    }

    public Employee(Integer id, String name, Position position, Department department, LocalDate hireDate, LocalDate fireDate) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.department = department;
        this.hireDate = hireDate;
        this.fireDate = fireDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getFireDate() {
        return fireDate;
    }

    public void setFireDate(LocalDate fireDate) {
        this.fireDate = fireDate;
    }
}

