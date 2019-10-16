package com.asd.employee.controller;

import com.asd.employee.model.Department;
import com.asd.employee.model.Employee;
import com.asd.employee.model.Position;
import com.asd.employee.repo.DepartmentRepo;
import com.asd.employee.repo.EmployeeRepo;
import com.asd.employee.repo.PositionRepo;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private PositionRepo positionRepo;

    private int departmentId;
    private int positionId;
    private int employeeId;

    private boolean initialized = false;

    @BeforeEach
    void setUp() {
        if (!initialized) {
            Department department = new Department("Department 1");
            department = departmentRepo.save(department);

            Position position = new Position("Manager");
            position = positionRepo.save(position);

            Employee employee = new Employee("Ivan Ivanov", position, department,
                    LocalDate.of(2010, 1, 1),
                    LocalDate.of(2010, 1, 2));

            employee = employeeRepo.save(employee);

            departmentId = department.getId();
            positionId = position.getId();
            employeeId = employee.getId();

            initialized = true;
        }
    }

    @Test
    void update_correct() throws Exception {
        String url = "/employee/" + employeeId;
        String newName = "Ivan Petrov";
        LocalDate newHireDate = LocalDate.of(2015, 1, 1);
        LocalDate newFireDate = LocalDate.of(2015, 1, 2);

        JSONObject putJSON = new JSONObject()
                .put("name", newName)
                .put("position", new JSONObject()
                        .put("id", positionId))
                .put("department", new JSONObject()
                        .put("id", departmentId))
                .put("hireDate", newHireDate)
                .put("fireDate", newFireDate);

        mvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.position.id").value(positionId))
                .andExpect(jsonPath("$.department.id").value(departmentId))
                .andExpect(jsonPath("$.hireDate").value(newHireDate.toString()))
                .andExpect(jsonPath("$.fireDate").value(newFireDate.toString()));

        Employee updatedEmployee = employeeRepo.findById(employeeId).orElse(new Employee());
        assertNotNull(updatedEmployee);
        assertEquals(newName, updatedEmployee.getName());
        assertEquals(newHireDate, updatedEmployee.getHireDate());
        assertEquals(newFireDate, updatedEmployee.getFireDate());
    }

    @Test
    void update_equalDates() throws Exception {
        String url = "/employee/" + employeeId;
        String newName = "Ivan Ivanov";
        LocalDate newHireDate = LocalDate.of(2018, 1, 1);
        LocalDate newFireDate = LocalDate.of(2018, 1, 1);

        JSONObject putJSON = new JSONObject()
                .put("name", newName)
                .put("position", new JSONObject()
                        .put("id", positionId))
                .put("department", new JSONObject()
                        .put("id", departmentId))
                .put("hireDate", newHireDate)
                .put("fireDate", newFireDate);

        mvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.position.id").value(positionId))
                .andExpect(jsonPath("$.department.id").value(departmentId))
                .andExpect(jsonPath("$.hireDate").value(newHireDate.toString()))
                .andExpect(jsonPath("$.fireDate").value(newFireDate.toString()));

        Employee updatedEmployee = employeeRepo.findById(employeeId).orElse(new Employee());
        assertNotNull(updatedEmployee);
        assertEquals(newName, updatedEmployee.getName());
        assertEquals(newHireDate, updatedEmployee.getHireDate());
        assertEquals(newFireDate, updatedEmployee.getFireDate());
    }

    @Test
    void update_wrongDates() throws Exception {
        Employee oldEmployee = employeeRepo.findById(employeeId).orElse(new Employee());
        LocalDate oldHireDate = oldEmployee.getHireDate();
        LocalDate oldFireDate = oldEmployee.getFireDate();

        String url = "/employee/" + employeeId;
        String newName = "Ivan Ivanov";
        LocalDate newHireDate = LocalDate.of(2011, 1, 1);
        LocalDate newFireDate = LocalDate.of(2010, 1, 1);

        JSONObject putJSON = new JSONObject()
                .put("name", newName)
                .put("position", new JSONObject()
                        .put("id", positionId))
                .put("department", new JSONObject()
                        .put("id", departmentId))
                .put("hireDate", newHireDate)
                .put("fireDate", newFireDate);

        mvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putJSON.toString()))
                .andExpect(status().isBadRequest());

        Employee updatedEmployee = employeeRepo.findById(employeeId).orElse(new Employee());
        assertNotNull(updatedEmployee);
        assertEquals(oldHireDate, updatedEmployee.getHireDate());
        assertEquals(oldFireDate, updatedEmployee.getFireDate());
    }


    @Test
    void update_wrongEmployeeId() throws Exception {
        String url = "/employee/" + 999999;
        String newName = "Ivan Ivanov";
        LocalDate newHireDate = LocalDate.of(2010, 1, 1);
        LocalDate newFireDate = LocalDate.of(2011, 1, 1);

        JSONObject putJSON = new JSONObject()
                .put("name", newName)
                .put("position", new JSONObject()
                        .put("id", positionId))
                .put("department", new JSONObject()
                        .put("id", departmentId))
                .put("hireDate", newHireDate)
                .put("fireDate", newFireDate);

        mvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putJSON.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_wrongDepartmentId() throws Exception {
        String url = "/employee/" + employeeId;
        String newName = "Ivan Ivanov";
        LocalDate newHireDate = LocalDate.of(2010, 1, 1);
        LocalDate newFireDate = LocalDate.of(2011, 1, 1);

        JSONObject putJSON = new JSONObject()
                .put("name", newName)
                .put("position", new JSONObject()
                        .put("id", positionId))
                .put("department", new JSONObject()
                        .put("id", 999999))
                .put("hireDate", newHireDate)
                .put("fireDate", newFireDate);

        mvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putJSON.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_wrongPositionId() throws Exception {
        String url = "/employee/" + employeeId;
        String newName = "Ivan Ivanov";
        LocalDate newHireDate = LocalDate.of(2010, 1, 1);
        LocalDate newFireDate = LocalDate.of(2011, 1, 1);

        JSONObject putJSON = new JSONObject()
                .put("name", newName)
                .put("position", new JSONObject()
                        .put("id", 999999))
                .put("department", new JSONObject()
                        .put("id", departmentId))
                .put("hireDate", newHireDate)
                .put("fireDate", newFireDate);

        mvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(putJSON.toString()))
                .andExpect(status().isNotFound());
    }
}