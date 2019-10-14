package com.asd.aggregateddata.client;

import com.asd.aggregateddata.model.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("employee-service")
public interface RestClient {

    @RequestMapping("/employee")
    List<Employee> getEmployees();
}
