package com.asd.aggregateddata.service;

import com.asd.aggregateddata.client.RestClient;
import com.asd.aggregateddata.dto.AverageWorkingTime;
import com.asd.aggregateddata.model.Department;
import com.asd.aggregateddata.model.Employee;
import com.asd.aggregateddata.model.Position;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class AverageTimeServiceTest {

    @MockBean
    private RestClient restClient;

    @Autowired
    private AverageTimeService averageTimeService;

    @Test
    void getAverageTime_noEmployees() {
        Mockito.when(restClient.getEmployees()).thenReturn(new ArrayList<>());

        assertEquals(
                new AverageWorkingTime(0).getAverageWorkingDays(),
                averageTimeService.getAverageTime().getAverageWorkingDays());
    }

    @Test
    void getAverageTime_1_Employee() {
        Department department = new Department(1, "Department 1");
        Position position = new Position(1, "Manager");
        Employee employee = new Employee(555, "Ivan Ivanov", position, department,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 11));

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        Mockito.when(restClient.getEmployees()).thenReturn(employeeList);

        assertEquals(
                new AverageWorkingTime(10).getAverageWorkingDays(),
                averageTimeService.getAverageTime().getAverageWorkingDays());
    }

    @Test
    void getAverageTime_2_Employees() {
        Department department = new Department(1, "Department 1");
        Position position = new Position(1, "Manager");

        Employee employee1 = new Employee(555, "Ivan Ivanov", position, department,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 6));

        Employee employee2 = new Employee(555, "Ivan Ivanov", position, department,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2010, 1, 21));

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);

        Mockito.when(restClient.getEmployees()).thenReturn(employeeList);

        assertEquals(
                new AverageWorkingTime(12.5).getAverageWorkingDays(),
                averageTimeService.getAverageTime().getAverageWorkingDays());
    }

    @Test
    void getAverageTime_3_Employees() {
        Department department = new Department(1, "Department 1");
        Position position = new Position(1, "Manager");

        Employee employee1 = new Employee(555, "Ivan Ivanov", position, department,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 6));

        Employee employee2 = new Employee(555, "Ivan Ivanov", position, department,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2010, 1, 21));

        Employee employee3 = new Employee(555, "Ivan Petrov", position, department,
                LocalDate.of(2001, 1, 1),
                LocalDate.of(2002, 1, 1));

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);

        Mockito.when(restClient.getEmployees()).thenReturn(employeeList);

        assertEquals(
                new AverageWorkingTime((5f + 20f + 365f) / 3).getAverageWorkingDays(),
                averageTimeService.getAverageTime().getAverageWorkingDays());
    }

    @Test
    void getAverageTime_wrongEmployees() {
        Department department = new Department(1, "Department 1");
        Position position = new Position(1, "Manager");

        Employee employee1 = new Employee(555, "Ivan Ivanov", position, department,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 6));

        Employee employee2 = new Employee(555, "Ivan Petrov", position, department,
                LocalDate.of(2010, 1, 2),
                LocalDate.of(2010, 1, 1));

        Employee employee3 = new Employee(555, "Ivan Ivanov", position, department,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2010, 1, 21));

        Employee employee4 = new Employee(555, "Ivan Petrov", position, department,
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2009, 2, 3));

        Employee employee5 = new Employee(555, "Ivan Ivanov", position, department,
                LocalDate.of(2001, 1, 1),
                LocalDate.of(2001, 2, 1));

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);
        employeeList.add(employee4);
        employeeList.add(employee5);

        Mockito.when(restClient.getEmployees()).thenReturn(employeeList);

        assertEquals(
                new AverageWorkingTime((5f + 0f + 20f + 0f + 31f) / 3).getAverageWorkingDays(),
                averageTimeService.getAverageTime().getAverageWorkingDays());
    }
}