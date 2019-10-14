package com.asd.aggregateddata.dto;

import java.math.BigDecimal;

public class AverageWorkingTime {
    private BigDecimal averageWorkingDays;

    public AverageWorkingTime() {
    }

    public AverageWorkingTime(double averageWorkingDays) {
        this.averageWorkingDays = new BigDecimal(averageWorkingDays).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getAverageWorkingDays() {
        return averageWorkingDays;
    }

    public void setAverageWorkingDays(BigDecimal averageWorkingDays) {
        this.averageWorkingDays = averageWorkingDays;
    }
}
