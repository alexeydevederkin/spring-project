package com.asd.aggregateddata.service;

import com.asd.aggregateddata.client.RestClient;
import com.asd.aggregateddata.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.util.Locale;


@Service
public class AverageTimeService {

    RestClient restClient;

    @Autowired
    public AverageTimeService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getAverageTime() {
        List<Employee> employees = restClient.getEmployees();

        long sumDays = 0;

        for (Employee employee : employees) {
            sumDays += ChronoUnit.DAYS.between(employee.getHireDate(), employee.getFireDate());
        }

        double averageDays = (double) sumDays / employees.size();

        return String.format(Locale.US, "Average working time in days: %.2f", averageDays);
    }
}
