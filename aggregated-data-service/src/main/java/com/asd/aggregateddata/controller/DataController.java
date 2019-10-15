package com.asd.aggregateddata.controller;

import com.asd.aggregateddata.dto.AverageWorkingTime;
import com.asd.aggregateddata.service.AverageTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data")
public class DataController {

    AverageTimeService averageTimeService;

    @Autowired
    public DataController(AverageTimeService averageTimeService) {
        this.averageTimeService = averageTimeService;
    }

    @GetMapping("/average-working-time")
    public AverageWorkingTime getAverageTime() {
        return averageTimeService.getAverageTime();
    }
}


