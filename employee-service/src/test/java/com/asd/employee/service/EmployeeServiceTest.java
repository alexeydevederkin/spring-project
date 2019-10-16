package com.asd.employee.service;

import com.asd.employee.exception.DepartmentNotFoundException;
import com.asd.employee.exception.IncorrectHireFireDateException;
import com.asd.employee.exception.PositionNotFoundException;
import com.asd.employee.model.Department;
import com.asd.employee.model.Employee;
import com.asd.employee.model.Position;
import com.asd.employee.repo.DepartmentRepo;
import com.asd.employee.repo.PositionRepo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class EmployeeServiceTest {

    @MockBean
    private DepartmentRepo departmentRepo;

    @MockBean
    private PositionRepo positionRepo;

    @Autowired
    private EmployeeService employeeService;

    private boolean initialized = false;

    @BeforeEach
    void setUp() {
        if (!initialized) {
            Department department = new Department("Department 1");
            department.setId(1);
            Mockito.when(departmentRepo.findById(1)).thenReturn(Optional.of(department));

            Position position = new Position("Manager");
            position.setId(1);
            Mockito.when(positionRepo.findById(1)).thenReturn(Optional.of(position));

            initialized = true;
        }
    }

    @Test
    void checkEmployeeCorrectness_correct() {
        Department department = new Department();
        department.setId(1);

        Position position = new Position();
        position.setId(1);

        Employee employee = new Employee("Ivan", position, department,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2010, 1, 2));

        assertDoesNotThrow(() -> employeeService.checkEmployeeCorrectness(employee));
    }

    @Test
    void checkEmployeeCorrectness_equalDates() {
        Department department = new Department();
        department.setId(1);

        Position position = new Position();
        position.setId(1);

        Employee employee = new Employee("Ivan", position, department,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2010, 1, 1));

        assertDoesNotThrow(() -> employeeService.checkEmployeeCorrectness(employee));
    }

    @Test
    void checkEmployeeCorrectness_wrongDates() {
        Department department = new Department();
        department.setId(1);

        Position position = new Position();
        position.setId(1);

        Employee employee = new Employee("Ivan", position, department,
                LocalDate.of(2010, 1, 2),
                LocalDate.of(2010, 1, 1));

        assertThrows(IncorrectHireFireDateException.class, () -> employeeService.checkEmployeeCorrectness(employee));
    }

    @Test
    void checkEmployeeCorrectness_noPosition() {
        Department department = new Department();
        department.setId(1);

        Employee employee = new Employee("Ivan", null, department,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2010, 1, 2));

        assertThrows(PositionNotFoundException.class, () -> employeeService.checkEmployeeCorrectness(employee));
    }

    @Test
    void checkEmployeeCorrectness_noDepartment() {
        Position position = new Position();
        position.setId(1);

        Employee employee = new Employee("Ivan", position, null,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2010, 1, 2));

        assertThrows(DepartmentNotFoundException.class, () -> employeeService.checkEmployeeCorrectness(employee));
    }

    @Test
    void checkEmployeeCorrectness_noHireDate() {
        Department department = new Department();
        department.setId(1);

        Position position = new Position();
        position.setId(1);

        Employee employee = new Employee("Ivan", position, department,
                null,
                LocalDate.of(2010, 1, 2));

        assertThrows(IncorrectHireFireDateException.class, () -> employeeService.checkEmployeeCorrectness(employee));
    }

    @Test
    void checkEmployeeCorrectness_noFireDate() {
        Department department = new Department();
        department.setId(1);

        Position position = new Position();
        position.setId(1);

        Employee employee = new Employee("Ivan", position, department,
                LocalDate.of(2010, 1, 2), null);

        assertThrows(IncorrectHireFireDateException.class, () -> employeeService.checkEmployeeCorrectness(employee));
    }

    @Test
    void checkEmployeeCorrectness_wrongDepartment() {
        Department department = new Department();
        department.setId(2);

        Position position = new Position();
        position.setId(1);

        Employee employee = new Employee("Ivan", position, department,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2010, 1, 2));

        assertThrows(DepartmentNotFoundException.class, () -> employeeService.checkEmployeeCorrectness(employee));
    }

    @Test
    void checkEmployeeCorrectness_wrongPosition() {
        Department department = new Department();
        department.setId(1);

        Position position = new Position();
        position.setId(2);

        Employee employee = new Employee("Ivan", position, department,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2010, 1, 2));

        assertThrows(PositionNotFoundException.class, () -> employeeService.checkEmployeeCorrectness(employee));
    }
}