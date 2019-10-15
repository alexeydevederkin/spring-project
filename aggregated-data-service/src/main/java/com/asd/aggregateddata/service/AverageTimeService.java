package com.asd.aggregateddata.service;

import com.asd.aggregateddata.client.RestClient;
import com.asd.aggregateddata.dto.AverageWorkingTime;
import com.asd.aggregateddata.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class AverageTimeService {

    RestClient restClient;

    @Autowired
    public AverageTimeService(RestClient restClient) {
        this.restClient = restClient;
    }

    public AverageWorkingTime getAverageTime() {
        List<Employee> employees = restClient.getEmployees();

        long sumDays = 0;
        long numberOfEmployees = 0;

        for (Employee employee : employees) {
            long days = ChronoUnit.DAYS.between(employee.getHireDate(), employee.getFireDate());

            // ignoring incorrect case with hireDate > fireDate, if it happens (it shouldn't)
            if (days >= 0) {
                sumDays += days;
                numberOfEmployees++;
            }
        }

        double averageDays = 0;

        // if there are no employees, then average working days = 0
        if (numberOfEmployees > 0) {
            averageDays = (double) sumDays / numberOfEmployees;
        }

        return new AverageWorkingTime(averageDays);
    }
}
